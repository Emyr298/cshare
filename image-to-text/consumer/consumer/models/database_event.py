from pydantic import BaseModel
from typing import TypeVar, Generic, Optional

T = TypeVar('T')

class DatabaseEventPayload(BaseModel, Generic[T]):
    op: str
    before: Optional[T]
    after: Optional[T]

class DatabaseEvent(BaseModel, Generic[T]):
    payload: DatabaseEventPayload[T]
