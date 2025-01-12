import requests
from typing import Dict, Any, Optional

from . import KafkaMessageConsumer
from models.database_event import DatabaseEvent
from models.content_resources import ContentResource
from models.image_to_text_result import ImageToTextResult
from publishers import KafkaMessageProducer

class ImageToTextConsumer(KafkaMessageConsumer):
    def __init__(self, producer: KafkaMessageProducer):
        super().__init__(self._message_processor)
        self.producer = producer
    
    def _message_processor(self, message: Optional[Dict[str, Any]]):
        if message is None:
            self.consumer.commit()
            return
        
        try:
            event = DatabaseEvent[ContentResource](**message)
        except Exception:
            return
        
        content_resource = event.payload.after
        if event.payload.op in ["c", "d"] and content_resource:
            response = requests.post("http://localhost:3000/image_to_text", data={
                "image_url": content_resource.url,
            }).json()
            result = ImageToTextResult(**response)
            self._send_event(content_resource, result.caption)
    
    def _send_event(self, content_resource: ContentResource, caption: str):
        self.producer.publish({
            "content_resource_id": content_resource.id,
            "content_id": content_resource.content_id,
            "text": caption,
        }, topic="content.image-to-text.done", key="done-"+content_resource.id)
