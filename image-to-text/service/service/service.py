import bentoml
import requests
from PIL import Image
from transformers import BlipProcessor, BlipForConditionalGeneration

MODEL_NAME = "Salesforce/blip-image-captioning-base"

@bentoml.service(
    resources={"cpu": "1", "memory": "2Gi"},
)
class ImageToText:
    def __init__(self):
        self.processor = BlipProcessor.from_pretrained(MODEL_NAME)
        self.model = BlipForConditionalGeneration.from_pretrained(MODEL_NAME)

    @bentoml.api
    def image_to_text(self, image_url: str):
        raw_image = Image.open(requests.get(image_url, stream=True).raw).convert('RGB')
        inputs = self.processor(raw_image, return_tensors="pt")
        out = self.model.generate(**inputs)
        return {
            "caption": self.processor.decode(out[0], skip_special_tokens=True)
        }
