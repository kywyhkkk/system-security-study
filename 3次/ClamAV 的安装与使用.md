# **ClamAV 安装与使用**

## **一、ClamAV简介**

ClamAV 是一个开源的杀毒引擎，主要用于检测恶意软件和病毒。它支持多种操作系统，包括 Linux、macOS 和 Windows。ClamAV 通常被用于邮件网关、文件服务器或其他需要扫描文件的场景。

------

## **二、安装 ClamAV**

### **2.1 在 Linux 上安装**

根据实验报告操作并搜索了网络上的相关资料，执行了下面的操作。

1. **更新软件包管理器**：

   ```bash
   sudo apt update
   sudo apt upgrade
   ```

2. **安装 ClamAV 和更新工具**：

   ```bash
   sudo apt install clamav clamav-daemon
   ```

3. **更新病毒库**： ClamAV 使用 `freshclam` 更新病毒定义。

   ```bash
   sudo freshclam
   ```

4. **启动 ClamAV 服务**：

   ```bash
   sudo systemctl start clamav-daemon
   sudo systemctl enable clamav-daemon
   ```

------

### **2.2 在 Windows 上安装**

这个好像比较麻烦，只是看了一下有关的安装过程。

1. **下载 ClamAV**：
   - 访问 ClamAV 官方网站 [ClamAV](https://www.clamav.net/)，下载适用于 Windows 的安装包。
2. **安装 ClamAV**：
   - 运行安装程序并按照提示完成安装。
3. **更新病毒库**：
   - 使用附带的 `freshclam` 工具进行病毒库更新。

------

## **三、使用 ClamAV**

参考了clamAV的使用文档与一些博客

### **3.1 基本命令**

1. **扫描文件或目录**：

   ```bash
   clamscan -r /path/to/scan
   ```

   参数说明：

   - `-r`：递归扫描目录。
   - `/path/to/scan`：要扫描的文件或目录路径。

2. **扫描并显示详细信息**：

   ```bash
   clamscan -r -v /path/to/scan
   ```

3. **扫描并输出感染结果**：

   ```bash
   clamscan --infected --remove -r /path/to/scan
   ```

   参数说明：

   - `--infected`：仅显示感染的文件。
   - `--remove`：自动删除感染文件。

------

### **3.2 使用 Daemon 模式**

ClamAV 的 `clamd` 是其守护进程，适用于实时扫描和高性能场景。

1. **配置 `clamd`**：

   - 编辑配置文件 `/etc/clamav/clamd.conf`。

   - 确保以下选项未被注释：

     ```plaintext
     LocalSocket /var/run/clamav/clamd.ctl
     TCPSocket 3310
     ```

2. **启动 `clamd` 服务**：

   ```bash
   sudo systemctl restart clamav-daemon
   ```

3. **使用 `clamdscan` 扫描**：

   ```bash
   clamdscan /path/to/scan
   ```

------

### **3.3 定时任务**

可以使用 `cron` 定时运行 ClamAV 扫描：

1. **编辑 cron 任务**：

   ```bash
   crontab -e
   ```

2. **添加扫描任务**：

   ```plaintext
   0 2 * * * clamscan -r /path/to/scan --log=/var/log/clamav_scan.log
   ```

------

## **四、常见问题与解决**

### **4.1 病毒库无法更新**

- 检查网络连接。

- 确保 

  ```
  freshclam
  ```

   服务正在运行：

  ```bash
  sudo systemctl restart clamav-freshclam
  ```

### **4.2 扫描速度慢**

- 使用 `--max-filesize` 和 `--max-scansize` 参数限制扫描文件大小。
- 配置 `clamd` 提高性能。