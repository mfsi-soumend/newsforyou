import React from "react";
import { Layout, theme } from "antd";
import SideBar from "../components/SideBar";
import News from "../news/News";
const { Content } = Layout;

const Home = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
    <Content style={{ padding: "0 50px" }}>
      <Layout style={{ padding: "24px 0", background: colorBgContainer }}>
        <SideBar colorBgContainer={colorBgContainer} />
        <Content style={{ padding: "0 24px", minHeight: 280 }}>
          <div className="news-wrapper-title">News</div>
          <News />
        </Content>
      </Layout>
    </Content>
  );
};

export default Home;
