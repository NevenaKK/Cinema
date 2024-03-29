import React, { lazy, useEffect, useState } from "react";
import CinemaAxios from "../../apis/CinemaAxios";
import {  useNavigate } from "react-router-dom";
import { Form,Button, Col, Row } from "react-bootstrap"

const AddMovie=()=>{

    const[newMovie,setNewMovie]=useState({
        name:"",
        duration:null,
        genre:null
    })

    const[genres,setGenres]=useState([])
    const[selectGenre,setSelectGenre]=useState([])
    
    const navigate=useNavigate();

    const valueInputChanged=(e)=>{
        const{name,value}=e.target;
        setNewMovie({...newMovie,[name]:value})  // vrednostima iz state dodeli vrednosti iz inputa
       // console.log({[e.target.name]: e.target.value})  
       // [e.target.name] --> name iz inputa 
       // e.target.value  --> value iz inputa 
       
    }
    
    const addMovie=()=>{
        CinemaAxios.post("/filmovi",{
            naziv: newMovie.name,
            trajanje: newMovie.duration,
            zanrovi: selectGenre
        })
            .then((res)=>{
                console.log(res)
                console.log(newMovie)
                navigate("/movies")
                
                
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    const getGenres=()=>{
        CinemaAxios.get("/zanrovi")
            .then((res)=>{
                console.log(res)
                setGenres(res.data)
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getGenres()
    },[])

    const handleGenresChanged=(genreId)=>{
        setSelectGenre({...selectGenre,[genreId]:true})
    }

    return(
        <Row>
              <Col>
                  <h1> Add movie </h1>
              </Col>

                <Col xs="12" sm="10" md="8"> 
                <Form>
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control id="name" name="name" onChange={valueInputChanged}></Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Duration</Form.Label>
                        <Form.Control type="number" name="duration" onChange={valueInputChanged}></Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Genres</Form.Label>
                        {
                            genres.map((genre)=>{
                                return <Form.Check  key={genre.id}
                                                    type="checkbox"
                                                    label={genre.naziv}
                                                    name="genres"
                                                    onChange={()=>handleGenresChanged(genre.id)} />
                            })
                        }
                    </Form.Group>

                    <br/>
                    <Button onClick={()=>addMovie()}> Add </Button>
                </Form>

                </Col>
                <Col></Col>
        </Row>
       

        
    )
}

export default AddMovie;