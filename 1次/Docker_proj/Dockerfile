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
