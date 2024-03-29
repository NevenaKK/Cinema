import React, { useEffect, useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import CinemaAxios from "../../apis/CinemaAxios";



const EditProjection=()=>{

    const routeParams=useParams()
    const projectionId=routeParams.id;

    const navigate=useNavigate()

    const[editProjection,setEditProjection]=useState({
        id:-1,
        movie:"",
        time:"",
        hall:-1,
        type:"",
        price:-1

    })

    const getProjectionById=(projectionId)=>{
        CinemaAxios.get("/projekcije/"+projectionId)
            .then((res)=>{
                console.log(res)
                setEditProjection({
                    id:res.data.id,
                    movie: res.data.filmId,
                    time:res.data.datumIVreme.replace("T"," "),
                    hall:res.data.sala,
                    type:res.data.tip,
                    price:res.data.cenaKarte
                })

            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getProjectionById(projectionId)
    
    },[])

    const inputValueChanged=(e)=>{
        const{name,value}=e.target
        setEditProjection({...editProjection,[name]:value})
    }

    const edit=(id)=>{
        CinemaAxios.put("/projekcije/"+id,{
            id:editProjection.id,
            filmId:editProjection.movie,
            datumIVreme:editProjection.time,
            sala:editProjection.hall,
            tip:editProjection.hall,
            cenaKarte:editProjection.price
        })
            .then((res)=>{
            console.log(res)
            navigate("/projections")
          

        })
        .catch((error)=>{
            console.log(error)
            console.log(editProjection)
        })
    }

    return(
        <Row>
            <Col> <h1>Edit projection</h1></Col>
            <Col>
            <Form>
                <Form.Group>
                    <Form.Label> Movie </Form.Label>
                    <Form.Control name="movie" value={editProjection.movie} onChange={inputValueChanged} />
                </Form.Group>
                <Form.Group>
                    <Form.Label> Time </Form.Label>
                    <Form.Control  name="time" value={editProjection.time.replace('T',' ') }onChange={inputValueChanged}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Hall </Form.Label>
                    <Form.Control type="number" name="hall" value={editProjection.hall}onChange={inputValueChanged} />
                </Form.Group>
                <Form.Group>
                    <Form.Label> Type </Form.Label>
                    <Form.Control name="type"value={editProjection.type} onChange={inputValueChanged}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> Price </Form.Label>
                    <Form.Control type="number" name="price" value={editProjection.price} onChange={inputValueChanged}/>
                </Form.Group>
                <br/>
                <Button onClick={()=>edit(editProjection.id)}>Edit</Button>
            </Form>
            </Col>
            <Col></Col>
        </Row>
    )
}

export default EditProjection;