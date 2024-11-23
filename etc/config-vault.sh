#!/bin/sh

AUTH_HEADER="X-Vault-Token: $VAULT_TOKEN"
CORE_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                "CONSUL_HOST": "localhost",
                "CONSUL_PORT": "8500",
                "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                "DB_HOST": "quangduong-azure-postgre.postgres.database.azure.com",
                "DB_PASSWORD": "QD@02092003",
                "DB_PORT": "5432",
                "DB_USERNAME": "quangduong",
                "KC_ADMIN_CLI_CLIENT": "admin-cli",
                "KC_HOMETOPIA_CLIENT": "hometopia-client",
                "KC_ADMIN_PASSWORD": "admin",
                "KC_ADMIN_USERNAME": "admin",
                "KC_REALM_HOMETOPIA": "hometopia",
                "KC_REALM_MASTER": "master",
                "KC_URI": "http://localhost:8008",
                "ZIPKIN_HOST": "localhost",
                "ZIPKIN_PORT": "9411"
            }
        }'
CORE_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "DB_HOST": "quangduong-azure-postgre.postgres.database.azure.com",
                  "DB_PASSWORD": "QD@02092003",
                  "DB_PORT": "5432",
                  "DB_USERNAME": "quangduong",
                  "KC_ADMIN_CLI_CLIENT": "admin-cli",
                  "KC_HOMETOPIA_CLIENT": "hometopia-client",
                  "KC_ADMIN_PASSWORD": "admin",
                  "KC_ADMIN_USERNAME": "admin",
                  "KC_REALM_HOMETOPIA": "hometopia",
                  "KC_REALM_MASTER": "master",
                  "KC_URI": "http://keycloak:8008",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'
GATEWAY_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                "CONSUL_HOST": "localhost",
                "CONSUL_PORT": "8500",
                "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                "ISSUER_URI": "http://localhost:8008/realms/hometopia",
                "KEYCLOAK_URL": "http://localhost:8008",
                "ZIPKIN_HOST": "localhost",
                "ZIPKIN_PORT": "9411"
            }
        }'
GATEWAY_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "ISSUER_URI": "http://keycloak:8008/realms/hometopia",
                  "KEYCLOAK_URL": "http://keycloak:8008",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'
MEDIA_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                  "CONSUL_HOST": "localhost",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "MINIO_BUCKET": "hometopia",
                  "MINIO_PASSWORD": "password",
                  "MINIO_URL": "http://3.0.184.197:9000",
                  "MINIO_USERNAME": "admin",
                  "ZIPKIN_HOST": "localhost",
                  "ZIPKIN_PORT": "9411"
             }
        }'
MEDIA_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "MINIO_BUCKET": "hometopia",
                  "MINIO_PASSWORD": "password",
                  "MINIO_URL": "http://3.0.184.197:9000",
                  "MINIO_USERNAME": "admin",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'
RULE_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                  "CONSUL_HOST": "localhost",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "ZIPKIN_HOST": "localhost",
                  "ZIPKIN_PORT": "9411"
             }
        }'
RULE_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'
SCHEDULED_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                  "CONSUL_HOST": "localhost",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "RABBITMQ_HOST": "localhost",
                  "RABBITMQ_PORT": "5672",
                  "RABBITMQ_USERNAME": "quangduong",
                  "RABBITMQ_PASSWORD": "292003",
                  "ZIPKIN_HOST": "localhost",
                  "ZIPKIN_PORT": "9411"
             }
        }'
SCHEDULED_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "RABBITMQ_HOST": "rabbitmq",
                  "RABBITMQ_PORT": "5672",
                  "RABBITMQ_USERNAME": "quangduong",
                  "RABBITMQ_PASSWORD": "292003",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'
VENDOR_DEV='{
             "options": {
                "cas": 0
             },
             "data": {
                  "CONSUL_HOST": "localhost",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "RABBITMQ_HOST": "localhost",
                  "RABBITMQ_PORT": "5672",
                  "RABBITMQ_USERNAME": "quangduong",
                  "RABBITMQ_PASSWORD": "292003",
                  "ZIPKIN_HOST": "localhost",
                  "ZIPKIN_PORT": "9411"
             }
        }'
VENDOR_PROD='{
              "options": {
                  "cas": 0
              },
              "data": {
                  "CONSUL_HOST": "consul",
                  "CONSUL_PORT": "8500",
                  "CONSUL_TOKEN": "6481160a-50e5-4fbe-a33d-aae7d368275f",
                  "RABBITMQ_HOST": "rabbitmq",
                  "RABBITMQ_PORT": "5672",
                  "RABBITMQ_USERNAME": "quangduong",
                  "RABBITMQ_PASSWORD": "292003",
                  "ZIPKIN_HOST": "zipkin",
                  "ZIPKIN_PORT": "9411"
              }
          }'

create_secret() {
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/core-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create CORE_DEV: $CORE_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/core-service/dev \
      -H "$AUTH_HEADER" \
      -d "$CORE_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/core-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create CORE_PROD: $CORE_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/core-service/prod \
      -H "$AUTH_HEADER" \
      -d "$CORE_PROD"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/gateway-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create GATEWAY_DEV: $GATEWAY_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/gateway-service/dev \
      -H "$AUTH_HEADER" \
      -d "$GATEWAY_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/gateway-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create GATEWAY_PROD: $GATEWAY_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/gateway-service/prod \
      -H "$AUTH_HEADER" \
      -d "$GATEWAY_PROD"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/media-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create MEDIA_DEV: $MEDIA_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/media-service/dev \
      -H "$AUTH_HEADER" \
      -d "$MEDIA_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/media-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create MEDIA_PROD: $MEDIA_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/media-service/prod \
      -H "$AUTH_HEADER" \
      -d "$MEDIA_PROD"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/rule-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create RULE_DEV: $RULE_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/rule-service/dev \
      -H "$AUTH_HEADER" \
      -d "$RULE_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/rule-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create RULE_PROD: $RULE_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/rule-service/prod \
      -H "$AUTH_HEADER" \
      -d "$RULE_PROD"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/scheduled-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create SCHEDULED_DEV: $SCHEDULED_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/scheduled-service/dev \
      -H "$AUTH_HEADER" \
      -d "$SCHEDULED_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/scheduled-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create SCHEDULED_PROD: $SCHEDULED_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/scheduled-service/prod \
      -H "$AUTH_HEADER" \
      -d "$SCHEDULED_PROD"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/vendor-service/dev")
    if [ "$status_code" -eq 404 ]; then
      echo "Create VENDOR_DEV: $VENDOR_DEV"
      curl -X POST http://localhost:8200/v1/hometopia/data/vendor-service/dev \
      -H "$AUTH_HEADER" \
      -d "$VENDOR_DEV"
    fi
    status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/data/vendor-service/prod")
    if [ "$status_code" -eq 404 ]; then
      echo "Create VENDOR_PROD: $VENDOR_PROD"
      curl -X POST http://localhost:8200/v1/hometopia/data/vendor-service/prod \
      -H "$AUTH_HEADER" \
      -d "$VENDOR_PROD"
    fi
}

echo "Vault token: $VAULT_TOKEN"
status_code=$(curl -o /dev/null -s -w "%{http_code}" -H "$AUTH_HEADER" "http://localhost:8200/v1/hometopia/config")

if [ "$status_code" -eq 404 ]; then
  echo "Secret engine not found"
  curl -X POST http://localhost:8200/v1/sys/mounts/hometopia \
  -H "$AUTH_HEADER" \
  -d '{"path":"hometopia","type":"kv","generate_signing_key":true,"config":{"max_lease_ttl":0,"listing_visibility":"hidden","id":"hometopia"},"options":{"version":2},"id":"hometopia"}'
  curl -X POST http://localhost:8200/v1/hometopia/config \
  -H "$AUTH_HEADER" \
  -d '{"max_versions":0,"cas_required":false,"delete_version_after":0}'
elif  [ "$status_code" -eq 200 ]; then
  echo "Secret engine already existed"
else
  echo "$status_code"
fi

create_secret
