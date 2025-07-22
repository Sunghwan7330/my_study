from fastapi import FastAPI, Depends, HTTPException, status
from sqlalchemy.orm import Session
from auth import authenticate_user, create_access_token, get_current_user, get_db, get_password_hash
import crud
import models
from schemas import UserCreate, UserLogin, Token
from database import engine
from passlib.context import CryptContext


models.Base.metadata.create_all(bind=engine)

app = FastAPI()

@app.post("/signup", response_model=Token)
def signup(user: UserCreate, db: Session = Depends(get_db)):
    db_user = crud.get_user_by_username(db, user.username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    crud.create_user(db, user.username, get_password_hash(user.password))
    access_token = create_access_token(data={"sub": user.username})
    return {"access_token": access_token, "token_type": "bearer"}

@app.post("/login", response_model=Token)
def login(user: UserLogin, db: Session = Depends(get_db)):
    db_user = authenticate_user(db, user.username, user.password)
    if not db_user:
        raise HTTPException(status_code=401, detail="Incorrect username or password")
    access_token = create_access_token(data={"sub": db_user.username})
    return {"access_token": access_token, "token_type": "bearer"}

@app.get("/protected")
def read_protected(current_user=Depends(get_current_user)):
    return {"message": "You are authorized", "user": current_user.username}
