import { useState } from "react";
import api from "../api/api";
import Input from "./Input";
import Button from "./Button";

export default function MetaForm({ pdiId, matricula, onSuccess }) {
  const [descricao, setDescricao] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/pdis/${matricula}/${pdiId}/metas`, { descricao, concluida: false });
      setDescricao("");
      onSuccess?.();
    } catch (err) {
      alert("Erro ao cadastrar Meta: " + (err.response?.data?.message || err.message));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="grid" style={{gap:8}}>
      <Input id="meta" label="Nova Meta" placeholder="Descrição da meta" value={descricao} onChange={(e)=>setDescricao(e.target.value)} />
      <div>
        <Button type="submit" size="small">Salvar meta</Button>
      </div>
    </form>
  );
}
