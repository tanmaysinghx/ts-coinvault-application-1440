<%@ page import="com.ts.model.User" %>
    <%@ page import="com.ts.model.Transaction" %>
        <%@ page import="java.util.List" %>
            <%@ page import="java.time.format.DateTimeFormatter" %>
                <%@ page contentType="text/html;charset=UTF-8" language="java" %>
                    <% User currentUser=(User) session.getAttribute("loggedUser"); List<Transaction> transactions =
                        (List<Transaction>) request.getAttribute("transactions");

                            // Security check handled by Servlet, but safe to keep here too
                            if (currentUser == null) { response.sendRedirect("login.jsp"); return; }
                            %>
                            <!DOCTYPE html>
                            <html>

                            <head>
                                <title>CoinVault - Dashboard</title>
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

                            <body class="p-10 flex flex-col items-center min-h-screen">

                                <div
                                    class="glass p-8 rounded-2xl shadow-2xl w-full max-w-2xl mb-8 flex justify-between items-center">
                                    <div>
                                        <h2 class="text-3xl font-bold text-white">
                                            <%= currentUser.getUsername() %>
                                        </h2>
                                        <p class="text-gray-400">Account #: <span class="font-mono text-yellow-500">
                                                <%= currentUser.getAccountNumber() !=null ?
                                                    currentUser.getAccountNumber() : "N/A" %>
                                            </span></p>
                                    </div>
                                    <div class="text-right">
                                        <p class="text-sm text-gray-400">Current Balance</p>
                                        <h1 class="text-4xl font-bold text-yellow-500">$<%= currentUser.getBalance() %>
                                        </h1>
                                    </div>
                                </div>

                                <% if (request.getAttribute("error") !=null) { %>
                                    <div
                                        class="w-full max-w-2xl bg-red-500/20 border border-red-500 text-red-200 p-3 rounded mb-8 text-sm text-center">
                                        <%= request.getAttribute("error") %>
                                    </div>
                                    <% } %>

                                        <div class="w-full max-w-2xl flex gap-4 mb-8">
                                            <a href="transfer.jsp"
                                                class="flex-1 bg-yellow-600 hover:bg-yellow-500 text-black font-bold py-3 rounded-lg text-center transition">
                                                Send Money
                                            </a>
                                            <button onclick="openModal('deposit')"
                                                class="flex-1 bg-green-600 hover:bg-green-500 text-white font-bold py-3 rounded-lg text-center transition">
                                                Deposit
                                            </button>
                                            <button onclick="openModal('withdraw')"
                                                class="flex-1 bg-red-600 hover:bg-red-500 text-white font-bold py-3 rounded-lg text-center transition">
                                                Withdraw
                                            </button>
                                            <a href="login.jsp"
                                                class="flex-1 bg-slate-700 hover:bg-slate-600 text-white font-bold py-3 rounded-lg text-center transition">
                                                Log Out
                                            </a>
                                        </div>

                                        <!-- Modal -->
                                        <div id="transactionModal"
                                            class="fixed inset-0 bg-black/80 hidden items-center justify-center backdrop-blur-sm z-50">
                                            <div class="glass p-8 rounded-2xl w-96">
                                                <h2 id="modalTitle" class="text-2xl font-bold text-white mb-4">
                                                    Transaction</h2>
                                                <form action="transaction" method="post" class="space-y-4">
                                                    <input type="hidden" name="action" id="modalAction">
                                                    <div>
                                                        <label class="block text-sm text-gray-400">Amount</label>
                                                        <input type="number" name="amount" step="0.01" required
                                                            class="w-full bg-slate-800 border border-slate-600 rounded p-2 focus:border-yellow-500 outline-none">
                                                    </div>
                                                    <div class="flex gap-2">
                                                        <button type="button" onclick="closeModal()"
                                                            class="flex-1 bg-slate-700 text-white py-2 rounded">Cancel</button>
                                                        <button type="submit"
                                                            class="flex-1 bg-yellow-600 text-black font-bold py-2 rounded">Confirm</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>

                                        <script>
                                            function openModal(action) {
                                                document.getElementById('transactionModal').classList.remove('hidden');
                                                document.getElementById('transactionModal').classList.add('flex');
                                                document.getElementById('modalAction').value = action;
                                                document.getElementById('modalTitle').innerText = action.charAt(0).toUpperCase() + action.slice(1);
                                            }
                                            function closeModal() {
                                                document.getElementById('transactionModal').classList.add('hidden');
                                                document.getElementById('transactionModal').classList.remove('flex');
                                            }
                                        </script>

                                        <div class="w-full max-w-2xl">
                                            <h3 class="text-xl font-bold text-gray-300 mb-4">Recent Transactions</h3>

                                            <div class="glass rounded-xl overflow-hidden">
                                                <table class="w-full text-left text-sm text-gray-400">
                                                    <thead class="bg-slate-900 text-gray-200 uppercase font-medium">
                                                        <tr>
                                                            <th class="px-6 py-4">Date</th>
                                                            <th class="px-6 py-4">Details</th>
                                                            <th class="px-6 py-4">Note</th>
                                                            <th class="px-6 py-4 text-right">Amount</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody class="divide-y divide-slate-700">
                                                        <% if (transactions !=null) { for (Transaction t : transactions)
                                                            { boolean
                                                            isIncoming=t.getReceiver().getId().equals(currentUser.getId());
                                                            String otherParty=isIncoming ? t.getSender().getUsername() :
                                                            t.getReceiver().getUsername(); String colorClass=isIncoming
                                                            ? "text-green-400" : "text-red-400" ; String sign=isIncoming
                                                            ? "+" : "-" ; %>
                                                            <tr class="hover:bg-slate-800/50 transition">
                                                                <td class="px-6 py-4">
                                                                    <%= t.getTimestamp().toString().replace("T", " "
                                                                        ).substring(0, 16) %>
                                                                </td>
                                                                <td class="px-6 py-4">
                                                                    <%= isIncoming ? "Received from" : "Sent to" %>
                                                                        <span class="text-white font-bold">
                                                                            <%= otherParty %>
                                                                        </span>
                                                                </td>
                                                                <td class="px-6 py-4 text-gray-400 italic">
                                                                    <%= t.getNote() !=null ? t.getNote() : "-" %>
                                                                </td>
                                                                <td
                                                                    class="px-6 py-4 text-right font-mono font-bold <%= colorClass %>">
                                                                    <%= sign %>$<%= t.getAmount() %>
                                                                </td>
                                                            </tr>
                                                            <% } } else { %>
                                                                <tr>
                                                                    <td colspan="4" class="px-6 py-4 text-center">No
                                                                        transactions found.</td>
                                                                </tr>
                                                                <% } %>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                            </body>

                            </html>