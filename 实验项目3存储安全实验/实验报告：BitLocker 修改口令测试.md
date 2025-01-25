### **实验报告：BitLocker 修改口令测试**

------

#### **实验目的**

验证 BitLocker 在修改口令时的原理和操作流程，包括：

1. 数据加密密钥（Key）的更新与保护状态变更。
2. 是否涉及扇区加密密钥的重新生成或数据重加密。
3. 修改口令的影响及实验后的数据完整性。

------

#### **实验设备与环境**

1. **实验设备**：
   - 老师发的windows10虚拟机
2. **实验环境**：
   - 系统版本：Windows 10 Pro 64-bit。
   - 磁盘分区：一块 100GB 的 NTFS 分区，已启用 BitLocker 加密。
3. **工具与软件**：
   - Windows 内置 BitLocker 管理工具。
   - 命令行工具（PowerShell 和 manage-bde）。

------

#### **实验步骤**

##### **1. 启用 BitLocker 加密**

1. 打开 **控制面板** -> **系统和安全** -> **BitLocker 驱动器加密**。
2. 选择要加密的分区（D盘），点击 **启用 BitLocker**。
3. 设置口令（如 `TestPassword123`），选择备份恢复密钥。
4. 启动加密并等待完成。

##### **2. 测试数据完整性**

- 创建一个测试文件（`test_file.txt`）并写入内容。

- 记录文件的哈希值以验证数据完整性：

  ```powershell
  Get-FileHash D:\test_file.txt
  ```

##### **3. 修改 BitLocker 口令**

- 打开命令提示符或 PowerShell，执行以下命令修改 BitLocker 口令：

  ```powershell
  manage-bde -changepassword D: 
  ```

- 输入旧口令（`TestPassword123`）。

- 设置新口令（如 `NewPassword456`）。

##### **4. 测试数据完整性（修改口令后）**

- 再次计算 

  ```
  test_file.txt
  ```

   的哈希值：

  ```powershell
  Get-FileHash D:\test_file.txt
  ```

- 比较修改前后的哈希值，验证文件内容是否一致。

##### **5. 验证口令变更后的解锁流程**

1. 锁定分区：

   ```powershell
   manage-bde -lock D:
   ```

2. 尝试使用新口令解锁：

   ```powershell
   manage-bde -unlock D: -pw
   ```

3. 验证解锁状态是否成功。

------

#### **实验结果**

1. **修改口令的过程**：
   - 口令修改仅更新了用于保护数据加密密钥的加密密钥（Derived Key）。
   - 扇区内容的加密密钥（Data Encryption Key）未发生变化，因此未触发数据重加密。
2. **数据完整性**：
   - `test_file.txt` 的哈希值在修改口令前后保持一致，证明修改口令不会影响已加密数据的完整性。
3. **解锁流程**：
   - 使用旧口令解锁失败。
   - 使用新口令成功解锁分区。

------

#### **结论**

1. **BitLocker 的加密原理**：
   - 扇区数据使用对称算法（如 AES）加密。
   - 数据加密密钥（Data Encryption Key）受到口令或恢复密钥的保护。
   - 修改口令仅更新了保护密钥的状态，而不影响数据加密密钥或已加密的数据。
2. **实验发现**：
   - 修改 BitLocker 口令是安全的，不会对数据完整性造成影响。
   - 数据重加密过程未触发，因此修改口令速度较快。
3. **与 TrueCrypt 的对比**：
   - BitLocker 和 TrueCrypt 的基本加密流程相似，都采用数据加密密钥和保护密钥分离的机制。
   - BitLocker 支持与 TPM 芯片集成，安全性更高，适合企业环境。

------

#### **改进建议**

1. **备份恢复密钥**：
   - 修改口令后立即更新并妥善保存恢复密钥，避免因口令遗忘导致数据不可访问。
2. **验证密码强度**：
   - 设置复杂的口令以增强保护密钥的安全性。
3. **日志记录**：
   - 在生产环境中执行口令修改操作时，建议保留操作日志，确保可追溯性。

------

#### **参考资料**

1. [Microsoft BitLocker 官方文档](https://learn.microsoft.com/en-us/windows/security/information-protection/bitlocker/bitlocker-overview)
2. [TrueCrypt 官方文档（存档）](https://www.truecrypt.org/docs/)
3. [PowerShell BitLocker Cmdlets](https://learn.microsoft.com/en-us/powershell/module/bitlocker/)