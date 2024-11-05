import dotenv
dotenv.load_dotenv()

import os, json

print(os.environ.get('IS_ENV_LOADED'))

def init_debezium():
    with open('config/debezium.conf', 'r') as file:
        conf = file.read().replace(
                '${POSTGRES_USER}', os.environ.get('POSTGRES_USER')
            ).replace(
                '${POSTGRES_PASSWORD}', os.environ.get('POSTGRES_PASSWORD')
            )
        conf = json.dumps(json.loads(conf))
    cmd = f'curl -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d \'{conf}\''
    docker_cmd = f"docker exec -it cshare-debezium-connector {cmd}"
    os.system(docker_cmd)

if __name__ == '__main__':
    init_debezium()
