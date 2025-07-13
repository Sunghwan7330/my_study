from sqlalchemy import Column, Integer, String
from sqlalchemy.types import UserDefinedType
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Geometry(UserDefinedType):
    def get_col_spec(self):
        return "GEOMETRY"

class Place(Base):
    __tablename__ = "places"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(50))
    location = Column(Geometry)  # POINT(x y)
