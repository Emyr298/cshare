import requests
from typing import Dict, Any, Optional

from . import KafkaMessageConsumer
from models.database_event import DatabaseEvent
from models.content_resources import ContentResource
from models.image_to_text_result import ImageToTextResult

class ImageToTextConsumer(KafkaMessageConsumer):
    def __init__(self):
        super().__init__(self._message_processor)
    
    def _message_processor(self, message: Optional[Dict[str, Any]]):
        if message is None:
            self.consumer.commit()
            return
        event = DatabaseEvent[ContentResource](**message)
        content_resource = event.payload.after
        if event.payload.op in ["c", "d"] and content_resource:
            response = requests.post("http://localhost:3000/image_to_text", data={
                "image_url": content_resource.url,
            }).json()
            result = ImageToTextResult(**response)
            print(result.caption)
