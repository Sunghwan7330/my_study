from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from sqlalchemy import func
from shapely.wkt import loads as load_wkt
from shapely.geometry import Point

from database import SessionLocal, engine
from models import Base, Place
from schemas import PlaceCreate, PlaceOut

Base.metadata.create_all(bind=engine)

app = FastAPI()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/places/", response_model=PlaceOut)
def create_place(place: PlaceCreate, db: Session = Depends(get_db)):
    wkt = f"POINT({place.longitude} {place.latitude})"
    
    #new_place = Place(name=place.name, location=wkt)
    new_place = Place(
        name=place.name,
        location=func.ST_GeomFromText(wkt)
    )
    db.add(new_place)
    db.commit()
    db.refresh(new_place)

    point = load_wkt(wkt)
    return PlaceOut(
        id=new_place.id,
        name=new_place.name,
        latitude=point.y,
        longitude=point.x
    )

@app.get("/places/", response_model=list[PlaceOut])
def get_places(db: Session = Depends(get_db)):
    places = db.query(
        Place.id,
        Place.name,
        func.ST_AsText(Place.location).label("location_wkt")
    ).all()

    results = []
    for p in places:
        point = load_wkt(p.location_wkt)
        results.append({
            "id": p.id,
            "name": p.name,
            "latitude": point.y,
            "longitude": point.x
        })

    return results

@app.put("/places/{place_id}", response_model=PlaceOut)
def update_place(place_id: int, new_data: PlaceCreate, db: Session = Depends(get_db)):
    place = db.query(Place).filter(Place.id == place_id).first()
    if not place:
        raise HTTPException(status_code=404, detail="Place not found")

    wkt = f"POINT({new_data.longitude} {new_data.latitude})"
    place.name = new_data.name
    place.location = func.ST_GeomFromText(wkt)
    db.commit()
    db.refresh(place)

    point = load_wkt(wkt)
    return PlaceOut(
        id=place.id,
        name=place.name,
        latitude=point.y,
        longitude=point.x
    )

@app.delete("/places/{place_id}")
def delete_place(place_id: int, db: Session = Depends(get_db)):
    place = db.query(Place).filter(Place.id == place_id).first()
    if not place:
        raise HTTPException(status_code=404, detail="Place not found")

    db.delete(place)
    db.commit()
    return {"message": "Place deleted"}
