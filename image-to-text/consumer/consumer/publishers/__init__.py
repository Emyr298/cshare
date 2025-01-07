from kafka import KafkaProducer
import json

from typing import Dict, Any, Optional
from datetime import datetime
import socket
from kafka.errors import KafkaError

from log import logger

class KafkaMessageProducer:
    def __init__(self):
        self.config = {
            'bootstrap_servers': 'localhost:9092',
            'client_id': f'producer-{socket.gethostname()}',
            'acks': 'all',
            'retries': 3,
            'batch_size': 16384,
            'linger_ms': 1,
            'compression_type': 'gzip',
            'max_request_size': 1048576,  # 1MB
            'request_timeout_ms': 30000,  # 30 seconds
            'value_serializer': lambda x: json.dumps(x).encode('utf-8'),
            'key_serializer': lambda x: x.encode('utf-8') if x else None,
        }
        
        self.producer = None
        self._setup_producer()
        
    def _setup_producer(self):
        try:
            self.producer = KafkaProducer(**self.config)
            logger.info("Kafka producer initialized successfully")
        except Exception as e:
            logger.error(f"Failed to create Kafka producer: {str(e)}")
            raise

    def publish(
        self,
        message: Dict[str, Any],
        topic: str,
        key: str,
    ) -> bool:
        if topic is None:
            raise ValueError("No topic specified and no default topic set")
        
        try:
            future = self.producer.send(
                topic=topic,
                value=message,
                key=key,
            )
            
            record_metadata = future.get(timeout=10)
            
            logger.info(
                f"Message published successfully to {topic} "
                f"offset: {record_metadata.offset}]"
            )
            return True
        except KafkaError as e:
            logger.error(f"Failed to publish message to {topic}: {str(e)}")
            return False
