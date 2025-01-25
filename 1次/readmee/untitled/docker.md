Docker 是一个开源平台，主要用于开发、发布和运行应用程序。它通过容器化技术将应用程序及其依赖打包成一个轻量级的可移植容器，能够跨平台运行。
我在windows环境上结合了chatgpt提供的教程，下面是我的一些学习笔记。

### Docker 基础使用步骤

（linux部分可能因为虚拟机的配置原因没有配置成功，在windows上成功配置）

#### 1. 安装 Docker
在不同平台上的安装方法略有不同。

- **Linux（如 Ubuntu）**:
  ```bash
  sudo apt-get update
  sudo apt-get install docker.io
  ```

- **macOS**:
  可以从 [Docker 官方网站](https://www.docker.com/products/docker-desktop/) 下载并安装 Docker Desktop。

- **Windows**:
  同样从 [Docker 官方网站](https://www.docker.com/products/docker-desktop/) 下载 Docker Desktop。

安装完成后，使用以下命令验证 Docker 是否安装成功：
```bash
docker --version
```

#### 2. 拉取镜像
Docker 镜像是运行容器的模板。可以从 Docker Hub 上拉取公共镜像。

例如，拉取一个官方的 Ubuntu 镜像：
```bash
docker pull ubuntu
```

#### 3. 运行容器
使用拉取的镜像创建并启动容器。例如，运行一个基于 Ubuntu 的容器并进入交互模式：
```bash
docker run -it ubuntu /bin/bash
```

`docker run` 命令会创建一个容器并启动它，其中：
- `-it` 是运行交互式终端的选项。
- `ubuntu` 是镜像名。
- `/bin/bash` 是容器内运行的命令。

#### 4. 查看运行的容器
要查看当前运行的容器，可以使用以下命令：
```bash
docker ps
```

如果想查看所有容器（包括停止的），使用：
```bash
docker ps -a
```

#### 5. 停止容器
要停止运行中的容器，使用：
```bash
docker stop <container_id>
```

`<container_id>` 可以从 `docker ps` 或 `docker ps -a` 中获取。

#### 6. 删除容器
如果你不再需要某个容器，可以删除它：
```bash
docker rm <container_id>
```

#### 7. 删除镜像
要删除某个镜像，使用：
```bash
docker rmi <image_id>
```

#### 8. 使用 Dockerfile 构建镜像
你可以通过编写 `Dockerfile` 定制自己的镜像。

例如，一个简单的 `Dockerfile`：
```Dockerfile
FROM ubuntu:latest
RUN apt-get update && apt-get install -y python3
CMD ["python3", "--version"]
```

然后，使用以下命令构建镜像：
```bash
docker build -t my-python-image .
```

#### 9. Docker Compose（编排多个容器）
如果你的应用需要多个容器一起运行（例如，前端、后端和数据库），可以使用 Docker Compose。

创建一个 `docker-compose.yml` 文件来定义你的服务。例如：
```yaml
version: '3'
services:
  web:
    image: nginx
    ports:
      - "8080:80"
  db:
    image: mysql
    environment:
      MYSQL_ROOT_PASSWORD: example
```

然后，使用以下命令启动所有服务：
```bash
docker-compose up
```

### 常用命令总结

- **拉取镜像**：`docker pull <image_name>`
- **运行容器**：`docker run -it <image_name> /bin/bash`
- **查看容器**：`docker ps` 或 `docker ps -a`
- **停止容器**：`docker stop <container_id>`
- **删除容器**：`docker rm <container_id>`
- **删除镜像**：`docker rmi <image_id>`
- **构建镜像**：`docker build -t <image_name> .`
- **启动 Docker Compose**：`docker-compose up`

之后尝试了一下docker desktop，图形化的操作更加的方便。

### 安装 Docker Desktop

1. **Windows**：
  - Docker Desktop 需要启用 Windows 的 Hyper-V 功能，Windows 10 Pro 或 Windows 11 是支持的系统。
  - 从 [Docker 官网](https://www.docker.com/products/docker-desktop) 下载 Docker Desktop 并安装。

安装完成后，启动 Docker Desktop，可以通过图形界面或命令行运行 Docker 容器。

### 使用 Docker Desktop

1. **启动 Docker Desktop**：
  - 打开 Docker Desktop 应用，启动时会自动启动 Docker 引擎。
  - 你可以在系统托盘中看到 Docker Desktop 的图标。

2. **使用 CLI**：
  - Docker Desktop 安装后，可以使用 Docker CLI 工具。例如：
    ```bash
    docker run hello-world
    ```
  - 这会拉取 `hello-world` 镜像并运行容器，以测试 Docker 是否工作正常。

3. **Kubernetes**：
  - 如果你需要使用 Kubernetes，可以在 Docker Desktop 设置中启用 Kubernetes，Docker 会自动配置并启动一个本地的 Kubernetes 集群。

![简单的hello word运行](./image/docker.jpg)