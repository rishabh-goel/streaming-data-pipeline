import time
import json
import boto3
import urllib


def lambda_handler(event, context):

    # boto3 client
    client = boto3.client("ec2")
    ssm = boto3.client("ssm")


    bucket = event['Records'][0]['s3']['bucket']['name']
    key = urllib.parse.unquote_plus(event['Records'][0]['s3']['object']['key'], encoding = 'utf-8')


    # getting instance information
    # describeInstance = client.describe_instances()

    with open("application.json", "r") as f:
        data = json.load(f)
    instanceid = data.get('aws-instance-id')
    shellscript = data.get('shellscript')
    # instanceid = "i-07927351fee3bfe4b"



    response = ssm.send_command(
        InstanceIds=[instanceid],
        DocumentName="AWS-RunShellScript",
        Parameters={
            "commands": ["sh /home/ec2-user/"+ shellscript +" "+ key]
        },  # replace command_to_be_executed with command
    )

    # fetching command id for the output
    command_id = response["Command"]["CommandId"]

    time.sleep(3)

    # fetching command output
    output = ssm.get_command_invocation(CommandId=command_id, InstanceId=instanceid)
    print(output)

    return {"statusCode": 200, "body": json.dumps(str(output))}