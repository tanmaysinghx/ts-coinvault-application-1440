<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <title>CoinVault - Transfer</title>
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
            <h2 class="text-2xl font-bold text-yellow-500 mb-6 text-center">Send Coins</h2>

            <% if (request.getAttribute("message") !=null) { %>
                <div class="bg-red-500/20 border border-red-500 text-red-200 p-3 rounded mb-4 text-sm text-center">
                    <%= request.getAttribute("message") %>
                </div>
                <% } %>

                    <form action="transfer" method="post" class="space-y-4">
                        <!-- Sender is now handled by session -->

                        <div>
                            <label class="block text-sm text-gray-400">To (Receiver Username)</label>
                            <input type="text" name="toUser" required
                                class="w-full bg-slate-800 border border-slate-600 rounded p-2 focus:border-yellow-500 outline-none">
                        </div>

                        <div>
                            <label class="block text-sm text-gray-400">Amount</label>
                            <input type="number" name="amount" step="0.01" required
                                class="w-full bg-slate-800 border border-slate-600 rounded p-2 focus:border-yellow-500 outline-none">
                        </div>

                        <div>
                            <label class="block text-sm text-gray-400">Note / Message</label>
                            <input type="text" name="note" placeholder="What's this for?"
                                class="w-full bg-slate-800 border border-slate-600 rounded p-2 focus:border-yellow-500 outline-none">
                        </div>

                        <button type="submit"
                            class="w-full bg-yellow-600 hover:bg-yellow-500 text-black font-bold py-2 rounded transition">
                            Transfer Funds
                        </button>
                    </form>

                    <div class="mt-4 text-center">
                        <a href="dashboard" class="text-xs text-gray-500 hover:text-white">Back to Dashboard</a>
                    </div>
        </div>
    </body>

    </html>