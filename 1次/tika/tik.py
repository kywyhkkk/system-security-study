from tika import parser


# 定义转换函数
def convert_to_txt(file_path, output_path):
    # 使用 tika.parser 从文件中提取内容
    parsed = parser.from_file(file_path)

    # 提取到的内容在 'content' 字段中
    content = parsed.get('content', '')

    # 将内容写入 .txt 文件
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"转换完成：{output_path}")


# 示例：将 PDF 转换为 TXT
convert_to_txt('example.pdf', 'output_pdf.txt')

# 示例：将 XLS 转换为 TXT
# convert_to_txt('example.xls', 'output_xls.txt')
