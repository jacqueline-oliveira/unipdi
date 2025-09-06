import { useState } from "react";
import api from "../api/api";
import Input, { Row } from "./Input";
import Button from "./Button";

export default function PessoaForm({ onSuccess }) {
  const [nome, setNome] = useState("");
  const [matricula, setMatricula] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/pessoas", { nome, matricula });
      setNome(""); setMatricula("");
      onSuccess?.();
    } catch (err) {
      alert("Erro ao cadastrar pessoa: " + (err.response?.data?.message || err.message));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="grid" style={{gap:12}}>
      <Row>
        <Input id="nome" label="Nome" placeholder="Nome" value={nome} onChange={(e)=>setNome(e.target.value)} required />
        <Input id="matricula" label="Matrícula" placeholder="Matrícula" value={matricula} onChange={(e)=>setMatricula(e.target.value)} required />
      </Row>
      <div style={{display:'flex', gap:8}}>
        <Button type="submit">Salvar</Button>
        <Button variant="ghost" onClick={()=>{ setNome(''); setMatricula(''); }}>Limpar</Button>
      </div>
    </form>
  );
}
