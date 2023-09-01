import React, { useState } from "react";
import { Layout } from "antd";
import SideBar from "../components/SideBar";
import News from "../news/News";
import NewsPage from "../components/NewsPage";
import { useNavigate, useParams } from "react-router-dom";
const { Content } = Layout;

const Home = () => {
  const navigate = useNavigate();
  let { id } = useParams();
  const [selectedCategories, setSelectedCategories] = useState([]);
  const onCategoryChange = (values) => {
    localStorage.setItem("selectedCategories", values);
    setSelectedCategories(values);
    navigate("/");
  };
  return (
    <Content style={{ padding: "0 50px" }}>
      <Layout
        style={{
          padding: "24px 0",
          background: "rgb(245 245 245)",
        }}
      >
        <SideBar onCategoryChange={onCategoryChange} />
        <Content style={{ padding: "0 24px", minHeight: 280 }}>
          {id ? (
            <NewsPage id={id} />
          ) : (
            <>
              <div className="news-wrapper-title">News</div>
              <News selectedCategories={selectedCategories} />
            </>
          )}
        </Content>
      </Layout>
    </Content>
  );
};

export default Home;
