import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/api";
import Header from "../components/Header";
import Card from "../components/Card";
import Button from "../components/Button";

export default function CurriculoPage() {
  const { matricula } = useParams();
  const navigate = useNavigate();
  const [curriculoUrl, setCurriculoUrl] = useState(null);
  const [file, setFile] = useState(null);

  // Carrega a URL do certificado ao montar a página
  const carregarCurriculo = async () => {
    try {
      const res = await api.get(`/curriculos/${matricula}/url`);
      // Verifica se a URL é válida
      if (res.data && res.data !== "") {
        setCurriculoUrl(res.data);
      } else {
        setCurriculoUrl(null);
      }
    } catch (err) {
      console.error(err);
      setCurriculoUrl(null);
    }
  };

  useEffect(() => {
    carregarCurriculo();
  }, [matricula]);

  const handleFileChange = (e) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      await api.post(`/curriculos/${matricula}/upload`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      setFile(null); // limpa seleção
      carregarCurriculo(); // atualiza link
      alert("Currículo enviado com sucesso!");
    } catch (err) {
      console.error(err);
      alert("Erro ao enviar currículo");
    }
  };

  return (
    <>
      <Header />
      <div className="container">
        <div style={{ display: "flex", alignItems: "center", gap: 12, marginBottom: 12 }}>
          <Button variant="ghost" onClick={() => navigate(-1)}>← Voltar</Button>
          <h2 className="sectionTitle">
            Currículo {matricula ? `(${matricula})` : ""}
          </h2>
        </div>

        <Card>
          <h3 className="sectionTitle">Gerenciar Currículo</h3>

          {curriculoUrl ? (
            <p style={{ marginTop: 12 }}>
              📎 <a href={curriculoUrl} target="_blank" rel="noreferrer">
                Abrir currículo
              </a>
            </p>
          ) : (
            <>
              <p className="subtle" style={{ marginBottom: 8 }}>
                Nenhum currículo cadastrado ainda.
              </p>
              <input type="file" accept=".pdf,.doc,.docx" onChange={handleFileChange} />
              {file && (
                <Button style={{ marginTop: 12 }} onClick={handleUpload}>
                  Enviar Currículo
                </Button>
              )}
            </>
          )}
        </Card>
      </div>
    </>
  );
}
