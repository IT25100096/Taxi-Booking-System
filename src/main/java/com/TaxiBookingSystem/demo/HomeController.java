package com.TaxiBookingSystem.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fleet Safety Audit</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { background: #0f0f0f; color: #f0f0f0; font-family: 'Inter', sans-serif; min-height: 100vh; padding: 40px; }

        /* HEADER */
        .header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 40px; flex-wrap: wrap; gap: 20px; }
        .header-left h1 { font-size: 2rem; font-weight: 800; color: #f5c518; letter-spacing: -0.5px; }
        .header-left p { margin-top: 6px; color: rgba(255,255,255,0.4); font-size: 0.9rem; }
        .header-right { display: flex; align-items: center; gap: 16px; }
        .critical-badge { display: flex; align-items: center; gap: 8px; background: #1a1a1a; border: 1px solid #333; border-radius: 999px; padding: 8px 16px; font-size: 0.85rem; }
        .critical-dot { width: 10px; height: 10px; background: #f5c518; border-radius: 50%; animation: pulse 2s infinite; }
        @keyframes pulse { 0%,100%{opacity:1} 50%{opacity:0.3} }
        .btn-export { background: #f5c518; color: #0f0f0f; border: none; border-radius: 8px; padding: 10px 20px; font-weight: 700; font-size: 0.9rem; cursor: pointer; font-family: 'Inter', sans-serif; }
        .btn-export:hover { background: #e0b310; }

        /* STAT CARDS */
        .stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; margin-bottom: 32px; }
        .stat-card { background: #1a1a1a; border: 1px solid #2a2a2a; border-radius: 12px; padding: 28px; }
        .stat-card.highlight { border-color: #f5c518; }
        .stat-label { font-size: 0.75rem; font-weight: 600; letter-spacing: 2px; text-transform: uppercase; color: rgba(255,255,255,0.4); margin-bottom: 12px; }
        .stat-value { font-size: 3rem; font-weight: 800; color: #f5c518; line-height: 1; }
        .stat-card:first-child .stat-value { color: #f0f0f0; }

        /* TABS */
        .nav-tabs { display: flex; gap: 8px; margin-bottom: 28px; flex-wrap: wrap; }
        .tab { padding: 8px 20px; border-radius: 8px; font-size: 0.85rem; font-weight: 600; border: 1px solid #2a2a2a; background: transparent; color: rgba(255,255,255,0.4); font-family: 'Inter', sans-serif; cursor: pointer; transition: all 0.2s; }
        .tab:hover, .tab.active { background: #f5c518; color: #0f0f0f; border-color: #f5c518; }

        /* PANELS */
        .panel { display: none; }
        .panel.active { display: block; }

        /* SECTION */
        .section { background: #1a1a1a; border: 1px solid #2a2a2a; border-radius: 12px; padding: 28px; margin-bottom: 24px; }
        .section-title { font-size: 1rem; font-weight: 700; color: #f5c518; margin-bottom: 24px; }

        /* TABLE */
        table { width: 100%; border-collapse: collapse; }
        th { text-align: left; font-size: 0.75rem; font-weight: 600; letter-spacing: 1.5px; text-transform: uppercase; color: rgba(255,255,255,0.35); padding: 0 12px 16px 12px; border-bottom: 1px solid #2a2a2a; }
        td { padding: 18px 12px; border-bottom: 1px solid #1f1f1f; font-size: 0.9rem; vertical-align: middle; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: rgba(255,255,255,0.02); }
        .vehicle-id { font-weight: 700; }

        /* BADGES */
        .badge { display: inline-block; padding: 4px 12px; border-radius: 999px; font-size: 0.75rem; font-weight: 600; }
        .badge-available { background: rgba(34,197,94,0.15);  color: #22c55e; }
        .badge-repair    { background: rgba(234,179,8,0.15);   color: #eab308; }
        .badge-booked    { background: rgba(168,85,247,0.15);  color: #a855f7; }
        .badge-ok        { background: rgba(34,197,94,0.15);   color: #22c55e; }
        .badge-overdue   { background: rgba(239,68,68,0.15);   color: #ef4444; }

        /* SEVERITY */
        .severity { display: inline-block; padding: 4px 12px; border-radius: 6px; font-size: 0.75rem; font-weight: 700; letter-spacing: 1px; text-transform: uppercase; }
        .sev-critical { background: rgba(239,68,68,0.2);   color: #ef4444; border: 1px solid rgba(239,68,68,0.3); }
        .sev-high     { background: rgba(249,115,22,0.2);  color: #f97316; border: 1px solid rgba(249,115,22,0.3); }
        .sev-low      { background: rgba(156,163,175,0.2); color: #9ca3af; border: 1px solid rgba(156,163,175,0.3); }
        .action-link { color: #f5c518; font-weight: 600; font-size: 0.85rem; text-decoration: none; cursor: pointer; }
        .action-link.red   { color: #ef4444; }
        .action-link.green { color: #22c55e; }
        .action-link:hover { text-decoration: underline; }

        /* SAFETY CHECK FORM */
        .form-card { background: #1c1c1c; border: 1px solid #2a2a2a; border-radius: 16px; padding: 40px; max-width: 700px; margin: 0 auto; }
        .form-card h2 { color: #f5c518; font-size: 1.8rem; font-weight: 800; text-align: center; margin-bottom: 8px; }
        .form-card .subtitle { color: rgba(255,255,255,0.4); text-align: center; font-size: 0.9rem; margin-bottom: 32px; }
        .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 24px; }
        .form-group { display: flex; flex-direction: column; gap: 8px; }
        .form-group label { font-size: 0.8rem; font-weight: 600; color: rgba(255,255,255,0.5); letter-spacing: 1px; text-transform: uppercase; }
        .form-group input, .form-group textarea, .form-group select {
            background: #111; border: 1px solid #2a2a2a; border-radius: 8px;
            color: #f0f0f0; padding: 12px 16px; font-family: 'Inter', sans-serif;
            font-size: 0.9rem; outline: none; transition: border-color 0.2s;
        }
        .form-group input:focus, .form-group textarea:focus, .form-group select:focus { border-color: #f5c518; }
        .form-group textarea { resize: vertical; min-height: 90px; }
        .checklist-box { background: #111; border: 1px solid #2a2a2a; border-radius: 12px; padding: 24px; margin-bottom: 24px; }
        .checklist-title { font-size: 0.95rem; font-weight: 700; color: #f5c518; margin-bottom: 20px; display: flex; align-items: center; gap: 8px; }
        .checklist-item { display: flex; align-items: center; gap: 14px; padding: 14px 0; border-bottom: 1px solid #1f1f1f; cursor: pointer; }
        .checklist-item:last-child { border-bottom: none; }
        .checklist-item input[type="checkbox"] { width: 20px; height: 20px; accent-color: #f5c518; cursor: pointer; flex-shrink: 0; }
        .checklist-item label { font-size: 0.95rem; cursor: pointer; color: rgba(255,255,255,0.8); }
        .checklist-item input:checked + label { color: #f5c518; }
        .btn-submit { width: 100%; background: #f5c518; color: #0f0f0f; border: none; border-radius: 10px; padding: 14px; font-weight: 700; font-size: 1rem; cursor: pointer; font-family: 'Inter', sans-serif; margin-top: 8px; transition: background 0.2s; }
        .btn-submit:hover { background: #e0b310; }
        .success-msg { display: none; background: rgba(34,197,94,0.1); border: 1px solid rgba(34,197,94,0.3); border-radius: 10px; padding: 16px; text-align: center; color: #22c55e; font-weight: 600; margin-top: 16px; }
    </style>
</head>
<body>

    <!-- HEADER -->
    <div class="header">
        <div class="header-left">
            <h1>Fleet Safety Audit</h1>
            <p>Real-time monitoring of at-risk and overdue vehicles.</p>
        </div>
        <div class="header-right">
            <div class="critical-badge"><span class="critical-dot"></span> 3 Critical Issues</div>
            <button class="btn-export">Export Report</button>
        </div>
    </div>

    <!-- STAT CARDS -->
    <div class="stats">
        <div class="stat-card"><div class="stat-label">Total Fleet</div><div class="stat-value">3</div></div>
        <div class="stat-card highlight"><div class="stat-label">Overdue Maintenance</div><div class="stat-value">2</div></div>
        <div class="stat-card highlight"><div class="stat-label">At-Risk Vehicles</div><div class="stat-value">2</div></div>
    </div>

    <!-- TABS -->
    <div class="nav-tabs">
        <button class="tab active" onclick="showTab('dashboard')">Dashboard</button>
        <button class="tab" onclick="showTab('vehicles')">Vehicles</button>
        <button class="tab" onclick="showTab('maintenance')">Maintenance</button>
        <button class="tab" onclick="showTab('incidents')">Safety Incidents</button>
        <button class="tab" onclick="showTab('safetycheck')">Pre-Shift Safety Check</button>
    </div>

    <!-- DASHBOARD PANEL -->
    <div class="panel active" id="dashboard">
        <div class="section">
            <div class="section-title">⚠ Action Required: At-Risk Vehicles</div>
            <table>
                <thead><tr><th>Vehicle ID</th><th>State</th><th>Issue / Reason</th><th>Severity</th><th>Action</th></tr></thead>
                <tbody>
                    <tr>
                        <td class="vehicle-id">V002</td>
                        <td><span class="badge badge-repair">Under-Repair</span></td>
                        <td>Brake failure reported by driver</td>
                        <td><span class="severity sev-high">HIGH</span></td>
                        <td><a class="action-link red" onclick="showTab('incidents')">👁 View Incident</a></td>
                    </tr>
                    <tr>
                        <td class="vehicle-id">V002</td>
                        <td><span class="badge badge-repair">Under-Repair</span></td>
                        <td>Brake Replacement overdue</td>
                        <td><span class="severity sev-critical">CRITICAL</span></td>
                        <td><a class="action-link red" onclick="showTab('maintenance')">👁 Recall</a></td>
                    </tr>
                    <tr>
                        <td class="vehicle-id">V001</td>
                        <td><span class="badge badge-available">Available</span></td>
                        <td>Oil Change scheduled</td>
                        <td><span class="severity sev-low">LOW</span></td>
                        <td><a class="action-link green" onclick="showTab('maintenance')">👁 Schedule Service</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- VEHICLES PANEL -->
    <div class="panel" id="vehicles">
        <div class="section">
            <div class="section-title">🚗 Full Fleet Overview</div>
            <table>
                <thead><tr><th>Vehicle ID</th><th>License Plate</th><th>Model</th><th>Status</th></tr></thead>
                <tbody>
                    <tr><td class="vehicle-id">V001</td><td>ABC-1234</td><td>Toyota Camry</td><td><span class="badge badge-available">Available</span></td></tr>
                    <tr><td class="vehicle-id">V002</td><td>XYZ-5678</td><td>Honda Civic</td><td><span class="badge badge-repair">Under-Repair</span></td></tr>
                    <tr><td class="vehicle-id">V003</td><td>LMN-9999</td><td>Ford Transit</td><td><span class="badge badge-booked">Booked</span></td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- MAINTENANCE PANEL -->
    <div class="panel" id="maintenance">
        <div class="section">
            <div class="section-title">🔧 Maintenance Reports</div>
            <table>
                <thead><tr><th>Report ID</th><th>Vehicle ID</th><th>Date</th><th>Service Type</th><th>Status</th></tr></thead>
                <tbody>
                    <tr><td class="vehicle-id">MR001</td><td>V001</td><td>2025-04-10</td><td>Oil Change</td><td><span class="badge badge-ok">On Time</span></td></tr>
                    <tr><td class="vehicle-id">MR002</td><td>V002</td><td>2025-03-01</td><td>Brake Replacement</td><td><span class="badge badge-overdue">Overdue</span></td></tr>
                    <tr><td class="vehicle-id">MR003</td><td>V002</td><td>2025-02-15</td><td>Tyre Rotation</td><td><span class="badge badge-overdue">Overdue</span></td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- INCIDENTS PANEL -->
    <div class="panel" id="incidents">
        <div class="section">
            <div class="section-title">⚠ Safety Incidents</div>
            <table>
                <thead><tr><th>Report ID</th><th>Vehicle ID</th><th>Date</th><th>Issue</th><th>Severity</th></tr></thead>
                <tbody>
                    <tr><td class="vehicle-id">SI001</td><td>V002</td><td>2025-03-05</td><td>Brake failure reported by driver</td><td><span class="severity sev-high">HIGH</span></td></tr>
                    <tr><td class="vehicle-id">SI002</td><td>V003</td><td>2025-04-20</td><td>Minor door dent from parking</td><td><span class="severity sev-low">LOW</span></td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- PRE-SHIFT SAFETY CHECK PANEL -->
    <div class="panel" id="safetycheck">
        <div class="form-card">
            <h2>Pre-Shift Safety Check</h2>
            <p class="subtitle">Please verify the health of your vehicle before starting your shift.</p>

            <div class="form-row">
                <div class="form-group">
                    <label>Vehicle ID</label>
                    <input type="text" id="vehicleId" placeholder="e.g. V-1024">
                </div>
                <div class="form-group">
                    <label>Date</label>
                    <input type="date" id="checkDate">
                </div>
            </div>

            <div class="checklist-box">
                <div class="checklist-title">☑ System Checklist</div>
                <div class="checklist-item">
                    <input type="checkbox" id="c1">
                    <label for="c1">Headlights, Taillights &amp; Signals functional</label>
                </div>
                <div class="checklist-item">
                    <input type="checkbox" id="c2">
                    <label for="c2">Tire pressure &amp; tread depth optimal</label>
                </div>
                <div class="checklist-item">
                    <input type="checkbox" id="c3">
                    <label for="c3">Brake responsiveness is normal</label>
                </div>
                <div class="checklist-item">
                    <input type="checkbox" id="c4">
                    <label for="c4">No warning lights on dashboard</label>
                </div>
                <div class="checklist-item">
                    <input type="checkbox" id="c5">
                    <label for="c5">Fuel level sufficient for shift</label>
                </div>
                <div class="checklist-item">
                    <input type="checkbox" id="c6">
                    <label for="c6">Mirrors and windshield clear</label>
                </div>
            </div>

            <div class="form-group" style="margin-bottom: 24px;">
                <label>Report Any Issues (Optional)</label>
                <textarea id="issues" placeholder="Describe any strange noises, vibrations, etc."></textarea>
            </div>

            <div class="form-group" style="margin-bottom: 24px;">
                <label>Driver Name</label>
                <input type="text" id="driverName" placeholder="Your full name">
            </div>

            <button class="btn-submit" onclick="submitCheck()">Submit Safety Check</button>
            <div class="success-msg" id="successMsg">✅ Safety check submitted successfully!</div>
        </div>
    </div>

    <script>
        // Set today's date on load
        document.getElementById('checkDate').valueAsDate = new Date();

        function showTab(name) {
            document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            document.getElementById(name).classList.add('active');
            document.querySelectorAll('.tab').forEach(t => {
                if (t.getAttribute('onclick').includes(name)) t.classList.add('active');
            });
        }

        function submitCheck() {
            const vehicleId  = document.getElementById('vehicleId').value.trim();
            const driverName = document.getElementById('driverName').value.trim();
            if (!vehicleId || !driverName) {
                alert('Please fill in Vehicle ID and Driver Name.');
                return;
            }
            const msg = document.getElementById('successMsg');
            msg.style.display = 'block';
            msg.textContent = '✅ Safety check for ' + vehicleId + ' submitted by ' + driverName + '!';
            // Reset form after 3 seconds
            setTimeout(() => {
                document.querySelectorAll('input[type="checkbox"]').forEach(c => c.checked = false);
                document.getElementById('vehicleId').value = '';
                document.getElementById('driverName').value = '';
                document.getElementById('issues').value = '';
                document.getElementById('checkDate').valueAsDate = new Date();
                msg.style.display = 'none';
            }, 3000);
        }
    </script>
</body>
</html>
        """;
    }
}
