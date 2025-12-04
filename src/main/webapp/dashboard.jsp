<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CoinVault - Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>body { background-color: #0f172a; color: #e2e8f0; }</style>
</head>
<body class="flex flex-col items-center justify-center h-screen">

    <div class="mb-6 bg-green-500/20 p-4 rounded-full border border-green-500">
        <svg class="w-12 h-12 text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>
    </div>

    <div class="bg-slate-800 p-8 rounded-2xl shadow-2xl w-96 border border-slate-700 text-center">
        <h2 class="text-2xl font-bold text-white mb-2">Registration Complete!</h2>
        <p class="text-gray-400 mb-6">Your wallet is active.</p>

        <div class="bg-slate-900 rounded-lg p-4 text-left space-y-3 mb-6">
            <div class="flex justify-between border-b border-slate-700 pb-2">
                <span class="text-gray-500 text-sm">Wallet ID</span>
                <span class="font-mono text-yellow-500">#${user.id}</span>
            </div>
            <div class="flex justify-between border-b border-slate-700 pb-2">
                <span class="text-gray-500 text-sm">Owner</span>
                <span class="font-medium text-white">${user.username}</span>
            </div>
            <div class="flex justify-between pt-1">
                <span class="text-gray-500 text-sm">Balance</span>
                <span class="font-bold text-green-400 text-lg">$${user.balance}</span>
            </div>
        </div>

        <a href="register.jsp" class="block w-full bg-slate-700 hover:bg-slate-600 text-white py-2 rounded-lg transition">
            Log Out / New User
        </a>
    </div>

</body>
</html>