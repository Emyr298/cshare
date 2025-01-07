from pydantic import BaseModel

class ImageToTextResult(BaseModel):
    caption: str
