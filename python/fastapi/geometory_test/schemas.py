from pydantic import BaseModel

class PlaceCreate(BaseModel):
    name: str
    latitude: float
    longitude: float

class PlaceOut(BaseModel):
    id: int
    name: str
    latitude: float
    longitude: float

    class Config:
        orm_mode = True
