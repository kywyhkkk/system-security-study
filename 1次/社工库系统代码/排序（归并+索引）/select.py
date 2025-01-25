import pyodbc

# 数据库连接参数
server = '211.87.227.229'
database = 'shegongku'  # 替换为你的数据库名
username = 'sdu'  # 替换为你的用户名
password = '1307'  # 替换为你的密码
port = '1433'  # SQL Server 默认端口
query = "SELECT * FROM your_table"  # 替换为你的查询语句
output_file = 'output.txt'  # 输出文件名

# 连接到 SQL Server
connection_string = f"DRIVER={{SQL Server}};SERVER={server},{port};DATABASE={database};UID={username};PWD={password}"
conn = pyodbc.connect(connection_string)
cursor = conn.cursor()

# 执行查询
cursor.execute(query)

# 获取列名
columns = [column[0] for column in cursor.description]

# 将结果写入 txt 文件
with open(output_file, 'w', encoding='utf-8') as f:
    # 写入列名
    f.write('\t'.join(columns) + '\n')
    
    # 写入每一行的数据
    for row in cursor.fetchall():
        row_data = [str(item) for item in row]  # 将每个元素转换为字符串
        f.write('\t'.join(row_data) + '\n')

# 关闭连接
cursor.close()
conn.close()

print(f"数据已成功保存到 {output_file}")
