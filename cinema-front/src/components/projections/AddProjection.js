import React, { useEffect, useState } from "react";
import { Form,Button, Col, Row } from "react-bootstrap";
import CinemaAxios from "../../apis/CinemaAxios";
import { useNavigate } from "react-router-dom";

const AddProjection=()=>{

    
    const navigate=useNavigate()
    const[newProjection,setNewProjection]=useState({
        movieId:null,
        time:null,
        hall:null,
        type:null,
        price:null
    })
    const[movies,setMovies]=useState([])

    const getMovie = ()=>{
        CinemaAxios.get("/filmovi")
            .then((res)=>{
                console.log(res)
                setMovies(res.data)
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getMovie()
    },[])

    const inputValueChanged=(e)=>{
        const {name,value}=e.target;
        console.log({[e.target.name]: e.target.value})  
        setNewProjection({...newProjection,[name]:value})
        console.log(newProjection)
    }

    const addProjection=()=>{
        CinemaAxios.post("/projekcije",{
            datumIVreme: newProjection.time,
            filmId: newProjection.movieId,
            sala:newProjection.hall,
            tip:newProjection.type,
            cenaKarte:newProjection.price
        })
            .then((res)=>{
                console.log(res.data)
                console.log(newProjection)
                navigate("/projections")
                
            })
            .catch((error)=>{
                console.log(error)
                console.log(newProjection)
            })
        
    }



    return(

        <Row>

            <Col>
                <h1> Add projection</h1>
            </Col>
            
            <Col xs="12" sm="10" md="8"> 
            <Form>
                <Form.Group>
                    <Form.Label>Movie</Form.Label>
                    <Form.Control id="movie" as='select' name="movieId" onChange={inputValueChanged}>
                        <option> Select Movie</option>
                        {
                            movies.map((movie)=>{
                                return <option key={movie.id} value={movie.id} >{movie.naziv}</option>
                            })
                        }
                    </Form.Control>
                </Form.Group>

                <Form.Group>
                    <Form.Label>Time</Form.Label>
                    <Form.Control id="time" name="time" onChange={inputValueChanged} />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Hall</Form.Label>
                    <Form.Control type="number" id="hall" name="hall" onChange={inputValueChanged} />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Type</Form.Label>
                    <Form.Control id="type" name="type" onChange={inputValueChanged} />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Price</Form.Label>
                    <Form.Control type="number" id="price" name="price" onChange={inputValueChanged} />
                </Form.Group>
                <br/>
                <Button onClick={()=>addProjection()}>Add</Button>
            </Form>
            </Col>
            <Col></Col>


        </Row>
       
    )
}

export default AddProjection