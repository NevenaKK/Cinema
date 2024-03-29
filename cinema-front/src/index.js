import React from 'react';
import { createRoot } from 'react-dom/client';
import {Link, Route,BrowserRouter as Router, Routes} from 'react-router-dom';
import Home from './components/Home';
import Movies from './components/movies/Movies';
import AddMovie from './components/movies/AddMovie';
import EditMovie from './components/movies/EditMovie';
import NotFound from './components/NotFound';
import Login from './components/authorization/Login';
import { logout } from './services/auth';
import { Button, Nav, Navbar, NavbarBrand } from 'react-bootstrap';
import Projections from './components/projections/Projections';
import AddProjection from './components/projections/AddProjection';
import EditProjection from './components/projections/EditProjection';


const App = () => {

    if(window.localStorage['jwt']){
        return( 
        
                <Router>

                <Navbar expand bg="dark" variant='dark'>   

                    <NavbarBrand as={Link} to="/"> Cinema </NavbarBrand>  
                    <Nav.Link as={Link} to="/movies">Movies</Nav.Link>  
                    <Nav.Link as={Link} to="/projections">Projections</Nav.Link>  
                    <Nav>
                        <Button onClick={()=>logout()} >Logout</Button>
                    </Nav>
                </Navbar>
    
                <Routes>
                    <Route path='/' element={<Home/>} />      
                    <Route path='/movies' element={<Movies/>} /> 
                    <Route path='/movies/add' element={<AddMovie/>}/>    
                    <Route path='/movies/edit/:id' element={<EditMovie/>}/>  
                    <Route path='/projections' element={<Projections/>} /> 
                    <Route path='/projections/add' element={<AddProjection/>}/>
                    <Route path='/projections/edit/:id' element={<EditProjection/>} />
                    <Route path='*'  element={<NotFound/>}/>
                    <Route path='/login' element={<Login/>} />
                </Routes>
                </Router>
    
    
        )
    }else{
        return( 
          
                <Router>
                <Navbar expand bg="dark" variant='dark'> 
                     <NavbarBrand as={Link} to="/"> Cinema </NavbarBrand>  
                     <Nav.Link as={Link} to="/movies">Movies</Nav.Link>
                     <Nav.Link as={Link} to="/projections">Projections</Nav.Link>  
    
                    <Nav.Link as={Link} to="/login" >Login</Nav.Link>
    
    
                </Navbar>
    
                <Routes>
                    <Route path='/' element={<Home/>} />      
                    <Route path='/movies' element={<Movies/>} /> 
                    <Route path='/projections' element={<Projections/>} />  
                    <Route path='/login' element={<Login/>} />
                    <Route path='*'  element={<NotFound/>}/>
                    
                </Routes>
                </Router>
    
    
        
    
        )
    }

    

};


const rootElement = document.getElementById('root');
const root = createRoot(rootElement);

root.render(
    <App />,
);
 