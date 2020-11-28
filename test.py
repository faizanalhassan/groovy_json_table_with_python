import json

json_data = open('sample.json').read()

def print_table(data):
    data = json.loads(data)
    if len(data):
        headers = data[0].keys()
        for header in headers:
            diff = len(str(data[0][header])) - len(header)
            if diff > 0:
                print(" "*diff, end='')
            print(header, end='\t')

        print()
        count = 0
        for d in data:
            for header in headers:
                diff = len(header) - len(str(data[0][header]))
                if diff > 0:
                    print(" "*diff, end='')
                print(d[header], end='\t')
            
            print()
            if count == 20:
                break
            count += 1

print_table(json_data)