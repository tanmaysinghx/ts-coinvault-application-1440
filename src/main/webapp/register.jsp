<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoinVault - Join</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body { background-color: #0f172a; color: #e2e8f0; }
        .glass { background: rgba(30, 41, 59, 0.7); backdrop-filter: blur(10px); border: 1px solid rgba(255, 255, 255, 0.1); }
    </style>
</head>
<body class="flex items-center justify-center h-screen">

    <div class="glass p-8 rounded-2xl shadow-2xl w-96">
        <div class="text-center mb-6">
            <h1 class="text-3xl font-bold text-yellow-500 tracking-wider">COINVAULT</h1>
            <p class="text-sm text-gray-400">Secure Enterprise Banking</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="bg-red-500/20 border border-red-500 text-red-200 p-3 rounded mb-4 text-sm text-center">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <form action="register" method="post" class="space-y-4">
            <div>
                <label class="block text-sm font-medium mb-1 text-gray-300">Username</label>
                <input type="text" name="username" required
                       class="w-full bg-slate-800 border border-slate-600 rounded-lg p-3 text-white focus:outline-none focus:border-yellow-500 transition">
            </div>

            <div>
                <label class="block text-sm font-medium mb-1 text-gray-300">Initial Deposit ($)</label>
                <input type="number" name="amount" value="1000" step="0.01" required
                       class="w-full bg-slate-800 border border-slate-600 rounded-lg p-3 text-white focus:outline-none focus:border-yellow-500 transition">
            </div>

            <button type="submit"
                    class="w-full bg-gradient-to-r from-yellow-600 to-yellow-500 hover:from-yellow-500 hover:to-yellow-400 text-black font-bold py-3 rounded-lg shadow-lg transform hover:scale-105 transition duration-200">
                Create Wallet
            </button>
        </form>

        <div class="mt-4 text-center text-xs text-gray-500">
            Powered by Java 25 & Jakarta EE
        </div>
    </div>

</body>
</html>