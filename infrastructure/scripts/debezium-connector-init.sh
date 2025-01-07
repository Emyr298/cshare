#!/bin/bash

echo "Waiting for Kafka Connect to be ready..."
while ! curl -s -f http://localhost:8083/connectors > /dev/null; do
    sleep 1
done

echo "Creating Debezium PostgreSQL connector..."
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
    http://localhost:8083/connectors/ -d '{
    "name": "content-connector",  
    "config": {
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "plugin.name": "pgoutput",
        "tasks.max": "1",  
        "database.hostname": "content-db",  
        "database.port": "5432",
        "database.user": "'"${CONTENT_POSTGRES_USER}"'",
        "database.password": "'"${CONTENT_POSTGRES_PASSWORD}"'",
        "database.server.id": "180000",  
        "database.dbname": "'"${CONTENT_POSTGRES_DB}"'",
        "topic.prefix": "contentdb",  
        "schema.history.internal.kafka.bootstrap.servers": "kafka:29092",  
        "schema.history.internal.kafka.topic": "schema-changes.content"  
    }
}'

echo "Connector creation completed."
