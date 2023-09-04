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
import Login from "../login/Login";
import Dashboard from "../admin/Dashboard";
import AgencyFeedPage from "../agencyFeedPage/AgencyFeedPage";

function Routes() {
  return (
    <Router>
      <Layout>
        <HeaderBar />
        <Switch>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/pageNotFound" element={<PageNotFound />} />
          <Route exact path="/news/:id" element={<Home />} />
          <Route exact path="/agency-feed/:id" element={<AgencyFeedPage />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/admin/dashboard" element={<Dashboard />} />
          <Route path="*" element={<Navigate to="/pageNotFound" />} />
        </Switch>
        <FooterBar />
      </Layout>
    </Router>
  );
}

export default Routes;
