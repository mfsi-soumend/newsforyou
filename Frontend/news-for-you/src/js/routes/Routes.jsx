import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes as Switch,
  Navigate,
} from "react-router-dom";
import Home from "../home/Home";
import PageNotFound from "../pageNotFound/PageNotFound";
import HeaderBar from "../components/HeaderBar";
import FooterBar from "../components/FooterBar";
import { Layout } from "antd";

function Routes() {
  return (
    <Router>
      <Layout>
        <HeaderBar />
        <Switch>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/pageNotFound" element={<PageNotFound />} />
          <Route path="*" element={<Navigate to="/pageNotFound" />} />
        </Switch>
        <FooterBar />
      </Layout>
    </Router>
  );
}

export default Routes;
