import groovy.json.JsonOutput
import groovy.json.JsonSlurper
def jsonSlurper = new JsonSlurper()
def data2 = jsonSlurper.parse(new File('sample.json'))

print(data2.getClass())

def print_table(def data){
    if(data.size()){
    def headers = data[0].keySet()
    for(header in headers) {
        // print(data[0][header].toString().size()-header.size())
        def diff = data[0][header].toString().size()-header.size()
        if(diff > 0){
            print(" "*diff)
        }
        print(header+"\t")
    }
    print("\n")
    def count = 0
    for (d in data){
        for(header in headers){
            def diff = header.size() - d[header].toString().size()
            if(diff > 0){
                print(" "*diff)
            }
            print(d[header]+"\t")
        }
        print("\n")
        if (count == 20){
            break
        }
        count += 1
    }
    // printf(headers)
    }
}
print_table(data2)