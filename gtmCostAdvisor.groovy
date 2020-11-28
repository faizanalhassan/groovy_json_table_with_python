import com.lmig.intl.gentium.integration.oauth2.RightSizingRecommendationsToken
import com.lmig.intl.gentium.integration.forge.RightSizingRecommendations
import groovy.json.JsonOutput
import groovy.json.JsonSlurper




def call(Map props, def cfnStackName) {
    echo "Available options:\n" +
            " - gtmCostAdvisor.cfnRightSizingRecommendations(cfnStackName:'${cfnStackName}')"
}


def cfnRightSizingRecommendations(Map props) {
        def token = getToken()
        def recommendations = RightSizingRecommendations.get(token, props.cfnStackName)
        def jsonRecommendations = JsonOutput.toJson(recommendations)
        // echo JsonOutput.prettyPrint(jsonRecommendations)
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
            d = data[count]
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
        def cmdArray = ["python3", "-c", py_code]
        def cmd = cmdArray.execute()
        cmd.waitForOrKill(2000)
        println cmd.text
        
}

private String getToken() {
    withCredentials([usernamePassword(credentialsId: 'FORGE_RIGHT_SIZING_RECOMMENDATIONS_TOKEN_PROD', usernameVariable: 'CLIENT_ID', passwordVariable: 'CLIENT_SECRET')]) {
        return RightSizingRecommendationsToken.instance.get(CLIENT_ID, CLIENT_SECRET)
    }
}

