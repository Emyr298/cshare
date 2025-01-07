from pydantic import BaseModel

class ContentResource(BaseModel):
    id: str
    content_id: str
    url: str
    created_at: int
