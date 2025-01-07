import json, os
from kafka import KafkaConsumer
from typing import Callable, Dict, Any

from log import logger

class KafkaMessageConsumer:
    def __init__(
        self,
        message_processor: Callable[[Dict[str, Any]], None],
    ):
        self.topic = 'contentdb.public.content_resources'
        self.message_processor = message_processor
        self.running = False
        self.consumer = None

        self.config = {
            'bootstrap_servers': 'localhost:9092',
            'group_id': os.urandom(16).hex(),
            'auto_offset_reset': 'earliest',
            'max_poll_interval_ms': 300000,  # 5 minutes
            'session_timeout_ms': 10000,     # 10 seconds
            'heartbeat_interval_ms': 3000,   # 3 seconds
            'value_deserializer': lambda x: json.loads(x.decode('utf-8')) if isinstance(x, bytes) else None
        }

    def start(self):
        try:
            self.consumer = KafkaConsumer(self.topic, **self.config)
            self.running = True
            
            logger.info(f"Started consuming from topic: {self.topic}")
            
            while self.running:
                for message in self.consumer:
                    logger.info("Consuming Mesage...")
                    self._process_message(message)
                    
        except Exception as e:
            logger.error(f"Fatal error in consumer: {str(e)}")
            
    def _process_message(self, message):
        try:
            self.message_processor(message.value if message is not None else None)
            self.consumer.commit()
        except Exception as e:
            logger.error(f"Error processing message: {str(e)}")
            raise
