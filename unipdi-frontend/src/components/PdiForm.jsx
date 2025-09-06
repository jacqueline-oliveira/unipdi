import { useState } from "react";
import api from "../api/api";
import Input, { Row } from "./Input";
import Button from "./Button";

export default function PdiForm({ pessoa, onSuccess }) {
  const [descricao, setDescricao] = useState("");
  const [dataInicio, setDataInicio] = useState("");
  const [dataFim, setDataFim] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!pessoa?.matricula) {
      alert("Erro: pessoa inválida ou matrícula não definida");
      return;
    }
    try {
      await api.post("/pdis", { matricula: pessoa.matricula, descricao, dataInicio, dataFim });
      setDescricao(""); setDataInicio(""); setDataFim("");
      onSuccess?.();
    } catch (err) {
      alert("Erro ao cadastrar PDI: " + (err.response?.data?.message || err.message));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="grid" style={{gap:12}}>
      <Input id="descricao" label={`Novo PDI para ${pessoa?.nome || '...'}`} placeholder="Descrição" value={descricao} onChange={(e)=>setDescricao(e.target.value)} required />
      <Row>
        <Input id="inicio" label="Início" type="date" value={dataInicio} onChange={(e)=>setDataInicio(e.target.value)} required />
        <Input id="fim" label="Término" type="date" value={dataFim} onChange={(e)=>setDataFim(e.target.value)} required />
      </Row>
      <div style={{display:'flex', gap:8}}>
        <Button type="submit">Salvar</Button>
        <Button variant="ghost" onClick={()=>{ setDescricao(''); setDataInicio(''); setDataFim(''); }}>Limpar</Button>
      </div>
    </form>
  );
}
