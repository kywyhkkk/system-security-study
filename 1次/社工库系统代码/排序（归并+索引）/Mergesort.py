#!/usr/bin/python3
# -*- coding:utf-8 -*-
from whoosh.qparser import QueryParser  
from whoosh.index import create_in  
from whoosh.index import open_dir  
from whoosh.fields import *  
from jieba.analyse import ChineseAnalyzer  
import io, os, sys, time
sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='utf-8')

# 导入中文分词工具
analyser = ChineseAnalyzer()
# 创建索引结构: 没有结构，就是很多文本文件，一行一行的，总之很多行
schema = Schema(full_line=TEXT(stored=True, analyzer=analyser))
# 数据和索引所在目录，以及索引名称  
ix = create_in("shegongku", schema=schema, indexname='allin1line')

# 返回root下的文件列表（不包含子目录）
def traverseFile(root):
    flist = []
    for f in os.listdir(root):
        f_path = os.path.join(root, f)
        if os.path.isfile(f_path):
            flist.append(f_path)
        else:
            flist += traverseFile(f_path)
    return flist

# 处理数据文件，每个文件每一行，加进去
writer = ix.writer()  
for fn in traverseFile("shegongku/db"):
    with open(fn, 'r', encoding='utf-8') as f:
        lines=0
        while True:
            line1 = f.readline()
            if line1:
                writer.add_document(full_line=line1)
                lines+=1;
            else:
                break
        print(fn, lines, "added")
writer.commit()  
print("index finished")

# 定义归并排序
def merge_sort(arr, key=lambda x: x):
    if len(arr) <= 1:
        return arr
    mid = len(arr) // 2
    left = merge_sort(arr[:mid], key)
    right = merge_sort(arr[mid:], key)
    
    return merge(left, right, key)

def merge(left, right, key):
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if key(left[i]) <= key(right[j]):
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result

# 搜索和排序
index1 = open_dir("shegongku", indexname='allin1line')
parser1 = QueryParser("full_line", index1.schema)

while True:
    with index1.searcher() as searcher:  
        print("pls input what u want to search:")
        key = input()
        myquery = parser1.parse(key)
        resultss = searcher.search(myquery, limit=2000)

        # 将搜索结果转换为列表
        results_list = [dict(result1)['full_line'] for result1 in resultss]

        # 按照文本长度进行排序
        sorted_results = merge_sort(results_list, key=lambda x: len(x))

        # 打印排序后的结果
        for result in sorted_results:
            print(result)
