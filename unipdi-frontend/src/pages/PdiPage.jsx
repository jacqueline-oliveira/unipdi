import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/api";
import PdiForm from "../components/PdiForm";
import MetaForm from "../components/MetaForm";
import Header from "../components/Header";
import Card from "../components/Card";
import Button from "../components/Button";

export default function PdiPage({ pessoa: pessoaProp }) {
  const navigate = useNavigate();
  const params = useParams();
  const [pessoa, setPessoa] = useState(pessoaProp || null);
  const [pdis, setPdis] = useState([]);
  const matricula = useMemo(() => pessoa?.matricula || params?.matricula, [pessoa, params]);

  const carregarPessoa = async () => {
    if (pessoaProp) return; // já veio por prop (compat)
    if (!params?.matricula) return;
    const res = await api.get(`/pessoas/${params.matricula}`);
    setPessoa(res.data);
  };

  const carregarPdis = async () => {
    if (!matricula) return;
    const res = await api.get(`/pdis/${matricula}`);
    setPdis(res.data);
  };

  useEffect(() => { carregarPessoa(); }, [params?.matricula]);
  useEffect(() => { if (matricula) carregarPdis(); }, [matricula]);

  return (
    <>
      <Header />
      <div className="container">
        <div style={{ display:'flex', alignItems:'center', gap:12, marginBottom:12 }}>
          <Button variant="ghost" onClick={() => navigate(-1)}>← Voltar</Button>
          <h2 className="sectionTitle">PDIs de {pessoa?.nome || ''} {matricula ? `(${matricula})` : ''}</h2>
        </div>

        <div className="grid grid2">
          <Card>
            <h3 className="sectionTitle">Cadastrar novo PDI</h3>
            <p className="subtle" style={{margin:'6px 0 12px'}}>Preencha os campos abaixo.</p>
            {pessoa && <PdiForm pessoa={pessoa} onSuccess={carregarPdis} />}
          </Card>

          <Card>
            <h3 className="sectionTitle">PDIs existentes</h3>
            <ul style={{listStyle:'none', marginTop:12}}>
              {pdis.map((pdi) => (
                <li key={pdi.id} style={{padding:'10px 0', borderBottom:'1px solid #eee'}}>
                  <div style={{fontWeight:700}}>{pdi.descricao}</div>
                  <div className="subtle">{pdi.dataInicio} até {pdi.dataFim}</div>

                  <ul style={{listStyle:'none', marginTop:8}}>
                    {pdi.metas.map((m) => (
                      <li key={m.id} style={{display:'flex', alignItems:'center', gap:8}}>
                        <span>{m.descricao}</span>
                        <span>{m.concluida ? "✅" : "❌"}</span>
                      </li>
                    ))}
                    {!pdi.metas?.length && <li className="subtle">Sem metas ainda.</li>}
                  </ul>

                  <div style={{marginTop:8}}>
                    <MetaForm pdiId={pdi.id} onSuccess={carregarPdis} />
                  </div>
                </li>
              ))}
              {!pdis.length && <li className="subtle">Nenhum PDI cadastrado para esta pessoa.</li>}
            </ul>
          </Card>
        </div>
      </div>
    </>
  );
}
