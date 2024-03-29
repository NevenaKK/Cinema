import React, { useCallback, useEffect, useState } from "react";
import CinemaAxios from "../../apis/CinemaAxios";
import { useNavigate } from "react-router-dom";
import{jwtDecode} from 'jwt-decode'
import {Form, Button, Col, Row, Table } from "react-bootstrap";


const Movies=()=>{

    const token=window.localStorage.getItem("jwt")
    const decoded=token?jwtDecode(token):null
    const isAdmin=decoded?.role?.authority==="ROLE_ADMIN"
    const isKorisnik=decoded?.role?.authority==="ROLE_KORISNIK"

    const [pageNo,setPageNo]=useState(0);
    const [pageMax,setPageMax]=useState(0);
    const [movies,setMovies]=useState([]) 
    const [sreach,setSreach]=useState({
        name:'',
        durationFrom:null,
        durationTo:null,
        genre:null
    })
    const [genres,setGenres]=useState([])
    const navigate=useNavigate();

    const getMovies=useCallback(()=>{
        CinemaAxios.get(`/filmovi?pageNo=${pageNo}`,{
            params:{
            naziv:sreach.name,
            trajanjeOd:sreach.durationFrom,
            trajanjeDo:sreach.durationTo,
            zanrId:sreach.genre
    }})
            .then(res=>{
                console.log(res)
                setMovies(res.data)
                setPageMax(res.headers['total-pages'])
                
            })
            .catch(error=>{
                console.log(error)
            })
    },[sreach,pageNo])


    const deleteMovies=(movieId)=>{
        CinemaAxios.delete("/filmovi/"+movieId)
            .then((res)=>{
                console.log(res)
                window.location.reload()
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    const goEdit=(movieId)=>{
        navigate("/movies/edit/"+movieId)
    }

    const renderMovies=()=>{
        return movies.map((movie)=>{
            return(
                <tr key={movie.id}>
                    <td>{movie.naziv}</td>
                    <td>{movie.trajanje}</td>
                    <td>{Object.values(movie.zanrovi).join(" , ")}</td>  
                    <td> {(isAdmin||isKorisnik) &&<Button onClick={()=>goEdit(movie.id)}> Edit</Button>}</td>
                    <td> {(isAdmin||isKorisnik) && <Button onClick={()=>deleteMovies(movie.id)}> Delete </Button>}</td>
                </tr>

                   //<td>{Object.values(movie.zanrovi).join(" , ")}</td>              ( u FilmDTO smo prosledili zanrove preko Map(Long,Naziv) ) 
            )      //<td> {movie.zanrovi.map((genre)=>genre.naziv).join(" , ")}</td>  ( u FilmDTO smo prosledili zanrove kao ZanrDTO )
        })
    }

    const goAdd=()=>{
        navigate("/movies/add")
    }

    const handleChange=(e)=>{
        const {name,value}=e.target;
        setSreach({...sreach,[name]:value})
        console.log(sreach)
    }

    const getGenres=()=>{
        CinemaAxios.get("/zanrovi")
            .then((res)=>{
                console.log(res)
                setGenres(res.data)
                console.log(res.data)
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getMovies()
        getGenres()
    },[pageNo])


    const handleSreach=()=>{
       getMovies()
    }

    return(
        <div>
            <Row>
                    <Row>
                        <h1>Movies</h1>
                    </Row>
            <div container="container">
                    <Col xs="12" sm="10" md="8"> 
                    
                    <Form>
                        <Form.Group>
                                <Form.Label> Name </Form.Label>
                                <Form.Control name="name" onChange={handleChange}/>
                        </Form.Group>
                        <Form.Group>
                                <Form.Label> Duration from </Form.Label>
                                <Form.Control type="number" name="durationFrom" onChange={handleChange}/>
                        </Form.Group>
                        <Form.Group>
                                <Form.Label> DurationTo </Form.Label>
                                <Form.Control type="number" name="durationTo" onChange={handleChange}/>
                        </Form.Group>
                        <Form.Group>
                                <Form.Label> Genre </Form.Label>
                                <Form.Control as="select" name="genre" onChange={handleChange}>
                                    <option value={null} key={null}> select genre </option>
                                    {
                                        genres.map((genre,index)=>{
                                            return <option key={genre.id} value={genre.id}> {genre.naziv}</option>
                                        })
                                    }
                                </Form.Control>
                        </Form.Group>

                        <div container='container' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>                      
                            <Button  style={{margin:'auto',marginTop:'30px'}} className="btn btn-success btn-lg" 
                                onClick={()=>handleSreach()} >Search</Button>                      
                        </div>
                    </Form>
                    </Col>
                </div>
            </Row>


            <div style={{ display: 'flex', justifyContent: "space-between" }}>
                
                {isAdmin && <Button onClick={()=>goAdd()}> Add </Button>}

                <span>
                    <Button disabled={pageNo<=0} onClick={(e)=>setPageNo(pageNo-1)}> Previous</Button>
                    <Button disabled={pageNo>=pageMax-1}onClick={(e)=>setPageNo(pageNo+1)}> Next </Button>
                </span>

            </div>
        

            <Row> 
            <Col>
                <Table>

                    <thead>
                        <tr>
                            <th> Name </th>
                            <th> Duration (min) </th>
                            <th> Genres </th>
                            <th> </th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        {renderMovies()}
                    </tbody>
                </Table>

            </Col>
            </Row>
        </div>
    )
}

export default Movies