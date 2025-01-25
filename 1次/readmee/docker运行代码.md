之后尝试了在docker上运行社工库项目，下面是具体的操作步骤，在操作的过程中遇到了因为网络问题无法从 Docker Hub 拉取 `debian:latest` 镜像时，无法与 Docker Hub 服务器建立连接。

经过查阅相关的解决方案，之后准备尝试使用阿里云的镜像添加到docker的配置文件中来进行解决。

### 1. 准备项目文件

首先，在你的本地机器上创建一个目录来存放项目文件，并准备好以下文件：

#### a. **`Dockerfile`**
这是用于构建 Docker 镜像的文件，将配置 Apache 服务器并启用 CGI 脚本。

```Dockerfile
# 使用官方的基础镜像
FROM debian:latest

# 更新包管理器并安装需要的软件
RUN apt-get update && apt-get install -y \
    apache2 \
    apache2-utils \
    libapache2-mod-cgi \
    nmap \
    python3 \
    python3-pip \
    curl \
    && apt-get clean

# 启用 Apache 的 CGI 模块
RUN a2enmod cgi

# 创建 CGI-bin 目录并设置权限
RUN mkdir -p /usr/lib/cgi-bin && chmod 755 /usr/lib/cgi-bin

# 复制网站的HTML和CGI脚本到相应的目录
COPY qqq-req-a1.htm /var/www/html/qqq-req-a1.htm
COPY qqq-req-a1.cgi /usr/lib/cgi-bin/qqq-req-a1.cgi
COPY py-mssql.py /usr/lib/cgi-bin/py-mssql.py  # 假设你有 py-mssql.py 文件

# 确保 CGI 脚本具有执行权限
RUN chmod +x /usr/lib/cgi-bin/qqq-req-a1.cgi
RUN chmod +x /usr/lib/cgi-bin/py-mssql.py

# 暴露80端口
EXPOSE 80

# 启动Apache服务器
CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
```

#### b. **`qqq-req-a1.htm`**
你提供的 HTML 文件。将其保存为 `qqq-req-a1.htm`。

```html
<html>
<head><meta charset="UTF-8"></head>
<head><title>QQqun2012查询</title></head>
<body>
<br>社工库之查询QQ群（2012leak）<br><br>
<form method="post" action="/cgi-bin/qqq-req-a1.cgi">
QQ号码：<textarea name="qqns" cols="40" rows="1">10001</textarea><br><br>
QQ群号：<textarea name="qqqs" cols="40" rows="1">1010101</textarea><br><br>
QQ昵称：<textarea name="qqnk" cols="40" rows="1" disabled=yes>张三 李4</textarea><br><br>
提交查询：<input type='submit' name="go" value="submit" />
<br><br>演示实现(2021.3-2023.12)<br>
</form>
</body>
</html>
```

#### c. **`qqq-req-a1.cgi`**
你提供的 CGI 脚本。将其保存为 `qqq-req-a1.cgi`。

```bash
#!/bin/bash

echo "Content-type: text/html; charset=utf-8"
echo ""
echo "<html>"
echo "<head>"
echo "<title>query req get/post ok</title>"
echo "</head>"
echo "<body>"

if [ "$REQUEST_METHOD" = "GET" ] ; then
    echo "hit GET"
    qqns=$(echo "$QUERY_STRING" | awk '{split($0,array,"&")} END{print array[1]}')
    qqqs=$(echo "$QUERY_STRING" | awk '{split($0,array,"&")} END{print array[2]}')
    export REQUEST_METHOD="get" 
elif [ "$REQUEST_METHOD" = "POST" ] ; then
    echo "hit POST"    
    OIFS="$IFS"
    IFS=\&
    read qqns qqqs go
    IFS="$OIFS"
fi
echo qqns: $qqns
echo qqqs: $qqqs
qqns=${qqns#qqns=}
qqqs=${qqqs#qqqs=}

echo "<br>qqnums: $qqns" 
echo "<br>groups: $qqqs"

qqns1=$(echo $qqns | sed -e 's/[^0-9]/ /g' | tr " " "\n" | sort | uniq | grep -v "^$" |
        awk '{ if ($1>10000 && $1<999999999999) print($1)}'| tail -100 | tr "\n" " ")
echo "<br>qqnums refined: $qqns1"
qqqs1=$(echo $qqqs | sed -e 's/[^0-9]/ /g' | tr " " "\n" | sort | uniq | grep -v "^$" |
        awk '{ if ($1>10000 && $1<999999999999) print($1)}'| tail -100 | tr "\n" " ")
echo "<br>groups refined: $qqqs1"

echo "<br>exec: py-mssql.py qq $qqns1 qqq $qqqs1"
echo "<pre>"
python3 /usr/lib/cgi-bin/py-mssql.py qq $qqns1 qqq $qqqs1
echo "</pre>"
echo "</body>"
echo "</html>"
```

#### d. **`py-mssql.py`**
假设你已经有了这个 Python 脚本，如果没有，你可以创建一个简单的 Python 脚本作为示例。

```python
#!/usr/bin/python3
import sys

# 打印传入的参数
print("Arguments: ", sys.argv)
```

### 2. 构建 Docker 镜像

- 打开 Docker Desktop，并确保它正在运行。
- 打开终端（Windows 用户可以使用 PowerShell 或命令提示符，macOS 和 Linux 用户使用终端）。
- 导航到你存放这些文件的目录。
  

运行以下命令来构建 Docker 镜像：

```bash
docker build -t my-apache-cgi .
```

### 3. 运行容器

构建镜像后，运行以下命令启动容器：

```bash
docker run -d -p 8080:80 my-apache-cgi
```

这会将容器的 80 端口映射到主机的 8080 端口，使得你可以通过 `http://localhost:8080` 访问。

### 4. 访问页面

- 打开浏览器，输入 `http://localhost:8080/qqq-req-a1.htm`。
- 你应该会看到表单页面，可以提交数据，CGI 脚本会处理表单内容并显示结果。

### 5. 停止容器

如果你想停止运行的容器，可以运行以下命令：

```bash
docker stop <container_id>
```

其中，`<container_id>` 可以通过以下命令获取：

```bash
docker ps
```

