Tor 桥接是一种将 Tor 流量通过中继节点发送的技术，能够绕过审查和封锁。Tor 桥接通常不公开，因此需要从 Tor 项目获取。

#### **2.1 通过 Tor 官方获取桥接**

1. 获取桥接地址

   ：

   - 你可以通过访问 [Tor Project 官方获取桥接页面](https://bridges.torproject.org/) 来获取桥接地址。
   - 或者你可以通过发送电子邮件请求获取桥接，方法如下：
     - 发送一封邮件到 `bridges@torproject.org`，邮件内容为空（没有主题或正文）。邮件会自动返回包含桥接地址的邮件。
   - 你还可以通过 Tor Browser 获取桥接：
     - 下载并打开 [Tor Browser](https://www.torproject.org/download/)，启动并在连接屏幕上选择 **Configure**。
     - 选择 **My Internet provider is censoring Tor**（我的互联网提供商正在审查 Tor）以获取桥接。

#### **2.2 使用 Telegram 获取桥接**

Tor 还提供通过 Telegram 获取桥接的方法。你可以通过以下步骤获取桥接：

1. 在 Telegram 中搜索 **@TorProject** 机器人。
2. 向机器人发送 `/getbridges` 命令，它会自动返回桥接地址。

------

### **3. 配置 Tor 使用桥接**

1. **编辑 Tor 配置文件**： 在默认情况下，Tor 会直接连接到 Tor 网络，但我们需要配置它使用桥接。要这样做，请编辑 Tor 配置文件：

   ```bash
   sudo nano /etc/tor/torrc
   ```

2. **添加桥接设置**： 在 `torrc` 文件中，找到并取消注释（删除 `#`）以下行：

   ```plaintext
   UseBridges 1
   ClientTransportPlugin obfs4 exec /usr/bin/obfs4proxy
   Bridge obfs4 <bridge_address>
   ```

   其中，`<bridge_address>` 是你从 Tor 项目或通过 Telegram 获取的桥接地址。一个桥接地址的格式可能像这样：

   ```plaintext
   Bridge obfs4 1.2.3.4:443 1234567890ABCDEF1234567890ABCDEF12345678
   ```

   你可以添加多个桥接，每个桥接一行。例如：

   ```plaintext
   Bridge obfs4 1.2.3.4:443 1234567890ABCDEF1234567890ABCDEF12345678
   Bridge obfs4 5.6.7.8:443 ABCDEF1234567890ABCDEF1234567890ABCDEF1234
   ```

   如果你获得的是 `obfs4` 类型的桥接，确保 `ClientTransportPlugin obfs4 exec /usr/bin/obfs4proxy` 这一行没有被注释。

3. **保存并退出**： 保存文件并退出编辑器。使用 `Ctrl + X`，然后按 `Y` 和 `Enter`。

------

### **4. 配置并启动 Tor**

1. **重启 Tor 服务**： 使新的配置生效，重启 Tor 服务：

   ```bash
   sudo systemctl restart tor
   ```

2. **检查 Tor 状态**： 确认 Tor 是否成功启动并通过桥接连接：

   ```bash
   sudo systemctl status tor
   ```

   如果一切配置正确，Tor 应该能够通过桥接连接到网络。

------

### **5. 测试 Tor 是否连接成功**

使用以下命令来确认 Tor 是否已成功连接：

```bash
tor --verify-config
```

如果配置正确，你会看到类似以下内容：

```
Oct 10 14:05:00.000 [Notice] Tor has successfully opened a circuit. Looks like the directory authority is reachable.
```

你还可以使用 **Tor Browser** 来测试是否能够成功访问被封锁的网站。确保浏览器已经配置为使用你的 Tor 网络。

------

### **6. 配置系统代理**

如果你希望通过 Tor 使用其他应用（如浏览器），你可以配置系统代理。将代理设置为 **127.0.0.1:9050**，这是 Tor 默认的 SOCKS 代理端口。

#### **6.1 配置系统代理**

1. 打开终端，编辑环境配置文件：

   ```bash
   nano ~/.bashrc
   ```

2. 在文件末尾添加以下代理配置：

   ```bash
   export http_proxy="socks5://127.0.0.1:9050"
   export https_proxy="socks5://127.0.0.1:9050"
   ```

3. 使配置生效：

   ```bash
   source ~/.bashrc
   ```

#### **6.2 配置浏览器代理**

- **Firefox**：进入 **Preferences** > **Network Settings**，选择 **Manual proxy configuration**，并设置 SOCKS 主机为 **127.0.0.1**，端口为 **9050**。

- Chrome

  ：在启动 Chrome 时使用命令行参数设置 SOCKS 代理：

  ```bash
  google-chrome --proxy-server="socks5://127.0.0.1:9050"
  ```

------

