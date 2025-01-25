### **Linux 内核权限控制与安全机制学习报告**

---

#### **1. 介绍**

Linux 是一个开源操作系统，提供了丰富的安全机制，尤其是在用户和文件访问控制方面。这些安全机制从内核层面实现了对用户和进程行为的监控，为系统的稳定性和安全性提供了保障。

本次任务的目标是通过代码阅读和实验实践，深入理解 Linux 内核的权限控制机制，并实现一些基本的内核定制和扩展功能，包括用户特权提升、文件权限修改以及系统调用监控，为未来的安全工具开发奠定基础。

---

#### **2. 实验任务**

##### **2.1 浏览 Linux 内核代码：权限控制实现**
1. **目标**：了解 `ls -l` 中显示的文件权限控制（如 `-rwxr-xr-x`）在内核中的实现。
2. **关键函数**：
   - 权限控制的核心函数是 `inode_permission`，位于内核 VFS（虚拟文件系统）中。
   - 函数路径：
     [inode_permission 源代码](https://elixir.bootlin.com/linux/latest/source/fs/namei.c#L1500)

3. **函数解析**：
   - `inode_permission` 函数的作用是根据 inode 信息和用户上下文检查文件权限。
   - 它结合用户的 UID、GID 以及文件的权限位（mode）判断是否允许访问。

4. **示例代码片段**：
   ```c
   static int inode_permission(struct inode *inode, int mask)
   {
       if (current_uid() == 1000) {
           return 0;  // 对 UID 为 1000 的用户，直接允许访问
       }
       return generic_permission(inode, mask);
   }
   ```

##### **2.2 给特定用户（如 UID 1000）增加隐式 root 权限**
1. **目标**：为 UID 1000 的用户提供隐式 root 权限。
2. **实现方式**：
   - 修改 `inode_permission` 函数，增加对 UID 1000 的特殊处理。
   - 或者编写一个内核模块，动态拦截权限检查。

3. **步骤**：
   1. 在 `inode_permission` 中插入条件语句，提前返回 `0`（允许访问）。
   2. 重新编译内核并安装。

4. **验证**：
   - 以 UID 1000 登录后，尝试访问需要 root 权限的文件或目录，确认无权限限制。

##### **2.3 允许任何用户访问指定文件**
1. **目标**：允许所有用户访问 `/tmp/somefile1`，无视文件权限。
2. **实现方式**：
   - 修改 `inode_permission` 函数，针对 `/tmp/somefile1` 的 inode 特殊处理。
   - 动态加载内核模块实现。

3. **代码实现**：
   ```c
   static int inode_permission(struct inode *inode, int mask)
   {
       if (strcmp(inode->i_path, "/tmp/somefile1") == 0) {
           return 0;  // 允许任何用户访问
       }
       return generic_permission(inode, mask);
   }
   ```

4. **验证**：
   
   - 使用普通用户尝试读取和写入 `/tmp/somefile1`，确认无权限限制。

##### **2.4 系统调用监控与实时病毒扫描**
1. **目标**：
   - 通过监控系统调用，捕获用户和进程对文件系统的操作，为病毒扫描程序提供支持。
   - 比较 `clamav` 的实时扫描机制。

2. **实现方式**：
   - 劫持系统调用表（sys_call_table），捕获文件相关调用如 `open`、`write`。
   - 使用 fanotify 机制捕获文件操作事件。

3. **代码示例：劫持 `open` 系统调用**：
   ```c
   asmlinkage long (*original_open)(const char __user *filename, int flags, umode_t mode);

   asmlinkage long hacked_open(const char __user *filename, int flags, umode_t mode)
   {
       printk(KERN_INFO "File opened: %s\n", filename);
       // 运行病毒扫描逻辑
       return original_open(filename, flags, mode);
   }

   static int __init syscall_init(void)
   {
       // 替换 sys_call_table 中的 open 系统调用
       original_open = (void *)sys_call_table[__NR_open];
       write_cr0(read_cr0() & (~0x10000)); // 禁用写保护
       sys_call_table[__NR_open] = (unsigned long)hacked_open;
       write_cr0(read_cr0() | 0x10000);  // 恢复写保护
       return 0;
   }
   ```

4. **比较 ClamAV 的 fanotify 机制**：
   - **Fanotify** 是 Linux 内核提供的文件系统事件通知接口，ClamAV 使用它捕获文件访问事件并触发扫描。
   - **优点**：集成度高，不需要劫持系统调用，维护性更好。
   - **缺点**：需要内核支持 fanotify，实时性可能受限。

---

#### **3. 报告总结**

##### **3.1 完成任务**
- 阅读并分析了 Linux 内核中权限控制的代码（`inode_permission`）。
- 修改代码为 UID 1000 的用户提供隐式 root 权限。
- 编写内核模块，允许任何用户访问指定文件。
- 劫持系统调用，实现对文件操作的实时监控，并初步设计了病毒扫描机制。

##### **3.2 学习收获**
- 理解了 Linux 文件权限控制的核心机制。
- 掌握了内核模块的开发与加载方法。
- 学习了系统调用的劫持技术及其应用场景。
- 了解了 ClamAV 的 fanotify 实现，并掌握了基本使用方法。

##### **3.3 后续优化**
- 探索更高效的权限控制方法，例如基于 LSM（Linux Security Module）。
- 深入研究 fanotify 的性能优化和扩展应用。
- 实现更复杂的病毒扫描逻辑，提升实时监控的能力。

---

#### **4. 参考资料**

1. **Linux 内核代码参考**：
   - [Linux Cross Reference (LXR)](https://elixir.bootlin.com/linux/latest/source)
2. **内核开发与调试**：
   - [Linux Kernel Module Programming Guide](https://tldp.org/LDP/lkmpg/2.6/html/)
3. **ClamAV 官方文档**：
   - [ClamAV Documentation](https://docs.clamav.net/)
4. **Fanotify 使用示例**：
   - [Fanotify Example Code](https://github.com/torvalds/linux/tree/master/samples/fanotify)

