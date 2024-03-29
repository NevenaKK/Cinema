import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CinemaAxios from "../../apis/CinemaAxios";
import { Button } from "react-bootstrap";

const EditMovie=()=>{

    const routeParams=useParams();
    let movieId=routeParams.id;

    const[updateMovie,setUpdateMovie]=useState({
        id:null,
        name:"",
        duration:null
    })
    const navigate=useNavigate()

    const getMovieById=(movieId)=>{
        CinemaAxios.get("/filmovi/" + movieId)
            .then((res)=>{
                console.log(res)
                setUpdateMovie({id: res.data.id,
                                name: res.data.naziv,
                                duration: res.data.trajanje})
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getMovieById(movieId)
        console.log(updateMovie)
    },[])

    const inputValueChanged=(e)=>{
        const{name,value}=e.target;
        setUpdateMovie({...updateMovie,[name]:value})
    }

    const edit=()=>{
        CinemaAxios.put("/filmovi/"+updateMovie.id,{
            id:updateMovie.id,
            naziv: updateMovie.name,
            trajanje:updateMovie.duration
        })
        .then((res)=>{
            console.log(res)
            navigate("/movies")
        })
        .catch((error)=>{
            console.log(error)
        })

    }

    return(

        <div>
            <h1> Edit movie </h1>
        
            <label> Name </label>
            <input type="text" name="name" value={updateMovie.name} onChange={inputValueChanged}/>
            <br/>
            <label> Duration  </label>
            <input type="number" name="duration" value={updateMovie.duration} onChange={inputValueChanged}/>
            <br/>
           
            <Button onClick={()=>edit()}>Edit</Button>
                
              

        </div>
       

        
    )
}

export default EditMovie;