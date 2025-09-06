import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Card from '../components/Card';
import Button from '../components/Button';

export default function Home(){
  return (
    <>
      <Header />
      <div className="container">
        <div className="grid grid2">
          <Card>
            <h2 className="sectionTitle">Cadastrar Pessoa</h2>
            <p className="subtle" style={{margin:'8px 0 16px'}}>Crie um novo registro de colaborador.</p>
            <Link to="/pessoas" className="link"><Button>Cadastrar</Button></Link>
          </Card>
          <Card>
            <h2 className="sectionTitle">Listar Pessoas</h2>
            <p className="subtle" style={{margin:'8px 0 16px'}}>Veja todas as pessoas e gerencie seus PDIs.</p>
            <Link to="/pessoas" className="link"><Button variant="ghost">Ver lista</Button></Link>
          </Card>
        </div>
      </div>
    </>
  );
}
