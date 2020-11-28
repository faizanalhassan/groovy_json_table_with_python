/* Hello World in Groovy */
def jsonRecommendations = """[{"id": 1}]"""
def py_code = """
import sys
import json
print(sys.version)
def print_table(data):
    data = json.loads(data)
    if len(data):
        # headers = data[0].keys()
        headers = ["id", "recommendation_id", "troux_uuid", "resource_id"]
        headers_str = "│"
        max_len = max([len(str(data[0].get(header, 'null'))) for header in headers])
        print(f"┌{'┬'.join(['─'*max_len for _ in headers])}┐")
        for header in headers:
            print("│", end='')
            diff = max_len - len(header)
            if diff > 0:
                print(" "*diff, end='')
                
            print(header, end='')
        print("│")
        print(f"├{'┼'.join(['─'*max_len for _ in headers])}┤")
        count = 0
        while True:
            d = data[0]
            for header in headers:
                print("│", end='')
                diff = max_len - len(str(data[0].get(header, 'null')))
                if diff > 0:
                    print(" "*diff, end='')
                print(d.get(header, 'null'), end='')
            print("│")
            if count < len(data) - 1:
                print(f"├{'┼'.join(['─'*max_len for _ in headers])}┤")
            else:
                print(f"└{'┴'.join(['─'*max_len for _ in headers])}┘")
                break
            
            count += 1
        
try:
    json_data = '''${jsonRecommendations}'''
    print_table(json_data)
        
except BaseException as e:
    print(e)
        """
def cmdArray = ["C:\\Users\\Faizan\\AppData\\Local\\Programs\\Python\\Python37\\python3.7.exe", "-c", py_code]
def cmd = cmdArray.execute()
cmd.waitForOrKill(10000)
println cmd.text