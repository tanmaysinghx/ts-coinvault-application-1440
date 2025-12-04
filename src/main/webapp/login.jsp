<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <title>CoinVault - Login</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <style>
            body {
                background-color: #0f172a;
                color: #e2e8f0;
            }

            .glass {
                background: rgba(30, 41, 59, 0.7);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.1);
            }
        </style>
    </head>

    <body class="flex items-center justify-center h-screen">

        <div class="glass p-8 rounded-2xl shadow-2xl w-96">
            <div class="text-center mb-6">
                <h1 class="text-3xl font-bold text-yellow-500 tracking-wider">COINVAULT</h1>
                <p class="text-sm text-gray-400">Welcome Back</p>
            </div>

            <% if (request.getAttribute("error") !=null) { %>
                <div class="bg-red-500/20 border border-red-500 text-red-200 p-3 rounded mb-4 text-sm text-center">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                    <form action="login" method="post" class="space-y-4">
                        <div>
                            <label class="block text-sm font-medium mb-1 text-gray-300">Username</label>
                            <input type="text" name="username" required
                                class="w-full bg-slate-800 border border-slate-600 rounded-lg p-3 text-white focus:outline-none focus:border-yellow-500 transition">
                        </div>

                        <div>
                            <label class="block text-sm font-medium mb-1 text-gray-300">Password</label>
                            <input type="password" name="password" required
                                class="w-full bg-slate-800 border border-slate-600 rounded-lg p-3 text-white focus:outline-none focus:border-yellow-500 transition">
                        </div>

                        <button type="submit"
                            class="w-full bg-gradient-to-r from-yellow-600 to-yellow-500 hover:from-yellow-500 hover:to-yellow-400 text-black font-bold py-3 rounded-lg shadow-lg transform hover:scale-105 transition duration-200">
                            Login
                        </button>
                    </form>

                    <div class="mt-4 text-center">
                        <p class="text-sm text-gray-400">New here? <a href="register.jsp"
                                class="text-yellow-500 hover:text-yellow-400 font-bold">Create an account</a></p>
                    </div>
        </div>

    </body>

    </html>