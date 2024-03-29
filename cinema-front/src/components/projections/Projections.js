import React, { useEffect, useState } from "react";
import CinemaAxios from "../../apis/CinemaAxios";
import { Button, Col, Row, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const Projections=()=>{

    const token=window.localStorage.getItem('jwt')
    const decoded=token?jwtDecode(token):null
    const isAdmin=decoded?.role?.authority==="ROLE_ADMIN"
    const isKorisnik=decoded?.role?.authority==="ROLE_KORISNIK"

    const[projections,setProjections]=useState([])
    const navigate=useNavigate()

    const[pageNo,setPageNo]=useState(0)
    const[maxPage,setMaxPage]=useState(0)

    const getProjections=()=>{
        CinemaAxios.get(`/projekcije?pageNo=${pageNo}`)
            .then((res)=>{
                console.log(res)
                setProjections(res.data)
                setMaxPage(res.headers['total-pages'])

            })
            .catch((error)=>{
                console.log(error)
            })
    }

    useEffect(()=>{
        getProjections()
    },[pageNo])

    const deleteProjection=(projectionId)=>{
            CinemaAxios.delete("/projekcije/"+projectionId)
            .then((res)=>{
                console.log(res)
                window.location.reload()
            })
            .catch((error)=>{
                console.log(error)
            })
    }

    const goEdit=(projectionId)=>{
        navigate("/projections/edit/"+projectionId)
    }

    const renderProjections=()=>{
        return projections.map((projection)=>{
            return(
                <tr key={projection.id}>
                    <td>{projection.filmNaziv}</td>
                    <td>{projection.datumIVreme.replace("T"," ")}</td>
                    <td>{projection.sala}</td>
                    <td>{projection.tip}</td>
                    <td>{projection.cenaKarte}</td>
                    <td>{(isAdmin || isKorisnik)&&<Button onClick={()=>goEdit(projection.id)}>Edit</Button>}</td>
                    <td>{(isAdmin || isKorisnik)&&<Button onClick={()=>deleteProjection(projection.id)}>Delete</Button>}</td>
                </tr>
            )
        })
    }

    const goAdd=()=>{
        navigate("/projections/add")
    }

    return(
        <div>
            
            <h1> Projections</h1>

            <div style={{ display: 'flex', justifyContent: "space-between" }}>
                {isAdmin && <Button onClick={()=>goAdd()}>Add</Button>}

                <span>
                    <Button disabled={pageNo<=0} onClick={(e)=>setPageNo(pageNo-1)}>Previous</Button>
                    <Button disabled={pageNo>=maxPage-1} onClick={(e)=>setPageNo(pageNo+1)}>Next</Button>
                </span>
            </div>
            
           
         <Row>
            <Col>
          
                <Table>
                    <thead>
                        <tr>
                            <th> Movie </th>
                            <th> Time </th>
                            <th> Hall </th>
                            <th> Type </th>
                            <th> Price</th>
                            <th></th>
                            <th></th>
                        
                        </tr>
                    </thead>
                        
                    <tbody>
                    {renderProjections()}
                    </tbody>
                </Table>

            </Col>
         </Row>

        </div>
    )
}

export default Projections