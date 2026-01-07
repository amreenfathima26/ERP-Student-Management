# 🚀 Deployment Guide: College ERP System

This guide provides a 100% complete walkthrough to deploy your **College ERP System** for free using the best available tools: **Render** for the Java Backend and **Vercel** for the React Frontend.

## 📋 Prerequisites
- A [GitHub](https://github.com/) account.
- A [Render](https://render.com/) account (Login with GitHub).
- A [Vercel](https://vercel.com/) account (Login with GitHub).
- [Git](https://git-scm.com/downloads) installed on your computer.

---

## Part 1: Push Code to GitHub

1.  **Initialize Git** (if not already done):
    Open your project folder in VS Code terminal:
    ```powershell
    git init
    # Add a .gitignore file if missing to ignore node_modules and target/
    echo "node_modules/" >> .gitignore
    echo "target/" >> .gitignore
    echo ".env" >> .gitignore
    ```
2.  **Commit Code**:
    ```powershell
    git add .
    git commit -m "Ready for deployment"
    ```
3.  **Create a Repo on GitHub**:
    - Go to GitHub -> New Repository.
    - Name it `college-erp`.
    - **Do NOT** check "Add a README" (keep it empty).
4.  **Push**:
    - Copy the commands shown by GitHub (under "…or push an existing repository from the command line"):
    ```powershell
    git remote add origin https://github.com/YOUR_USERNAME/college-erp.git
    git branch -M main
    git push -u origin main
    ```

---

## Part 2: Deploy Backend to Render (Free)

Render will host your Spring Boot Application.

1.  **Create a Web Service**:
    - Go to [Render Dashboard](https://dashboard.render.com/).
    - Click **New** -> **Web Service**.
    - Connect your `college-erp` repository.
2.  **Configure Service**:
    - **Name**: `college-erp-backend` (or unique name).
    - **Root Directory**: `backend` (⚠️ Important: type `backend` here).
    - **Runtime**: `Docker`.
    - **Instance Type**: Free.
3.  **Environment Variables**:
    - Click **Advanced** or **Environment Variables**.
    - Add the following keys (You can use the default H2 database for a quick demo, but data resets on restart. For persistent data, use a free MySQL provider like Aiven, but for now we'll stick to H2 for simplicity unless you have a MySQL URL):
        - `JWT_SECRET`: (Enter a long random string of text)
        - `CORS_ORIGINS`: `https://college-erp-frontend.vercel.app` (You will update this later after Frontend deploy, for now just put `*`).
4.  **Deploy**:
    - Click **Create Web Service**.
    - Wait for the build to finish. It might take 5-10 minutes.
    - **Copy the URL**: Once live, copy your backend URL (e.g., `https://college-erp-backend.onrender.com`).

---

## Part 3: Deploy Frontend to Vercel (Free)

Vercel will host your React Application.

1.  **Import Project**:
    - Go to [Vercel Dashboard](https://vercel.com/dashboard).
    - Click **Add New** -> **Project**.
    - Import `college-erp`.
2.  **Configure Project**:
    - **Framework Preset**: Vite.
    - **Root Directory**: Click "Edit" and select `frontend`.
3.  **Environment Variables**:
    - Expand **Environment Variables**.
    - Key: `VITE_API_BASE_URL`
    - Value: `https://college-erp-backend.onrender.com` (Paste your Render Backend URL here).
4.  **Deploy**:
    - Click **Deploy**.
    - Wait for it to complete (~1 minute).
    - **Copy the Domain**: You will get a URL like `https://college-erp-frontend.vercel.app`.

---

## Part 4: Connect Them (Final Polish)

Now that both are online, link them securely.

1.  **Update Backend CORS**:
    - Go back to **Render Dashboard** -> Your Service -> **Environment Variables**.
    - Edit `CORS_ORIGINS`.
    - Value: `https://college-erp-frontend.vercel.app` (Your actual Vercel domain).
    - **Save Changes**. Render will redeploy automatically.

2.  **Test**:
    - Open your Vercel URL.
    - Try to Login or Signup.
    - If it works, **Congratulations! 🚀**

---

## 🛠 Troubleshooting

- **Backend "Health Check Failed"**: Render might be slow to start (Free tier). Check logs. If it takes too long, just wait a bit and try accessing the URL manually.
- **Frontend "Network Error"**:
    - Check if `VITE_API_BASE_URL` in Vercel is correct (no trailing slash).
    - Check browser console (F12) for CORS errors. If CORS error, ensure Backend `CORS_ORIGINS` exactly matches your Vercel URL.
- **Data Disappears?**:
    - On Render Free Tier with H2 Database, data is lost when the server restarts (which happens on new deploys or inactivity).
    - **Fix**: Connect an external MySQL database (e.g., Aiven Free Tier) and set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` in Render Environment Variables.
