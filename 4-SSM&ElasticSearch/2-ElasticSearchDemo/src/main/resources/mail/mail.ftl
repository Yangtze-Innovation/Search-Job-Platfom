<body style="color: #666; font-size: 14px; font-family: 'Open Sans',Helvetica,Arial,sans-serif;">
<div class="box-content" style="width: 80%; margin: 20px auto; max-width: 800px; min-width: 600px;">
    <div class="header-tip" style="font-size: 12px;
                                   color: #aaa;
                                   text-align: right;
                                   padding-right: 25px;
                                   padding-bottom: 10px;">
        Confidential - Scale Alarm Use Only
    </div>
    <div class="info-top" style="padding: 15px 25px;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            background: ${bgColor};
            color: #fff;
            overflow: hidden;
            line-height: 32px;">
        <img src="cid:icon-alarm" style="float: left; margin: 0 10px 0 0; width: 32px;"/>
        <div style="color:#010e07"><strong>服务实例水平伸缩通知</strong></div>
    </div>
    <div class="info-wrap" style="border-bottom-left-radius: 10px;
                                  border-bottom-right-radius: 10px;
                                  border:1px solid #ddd;
                                  overflow: hidden;
                                  padding: 15px 15px 20px;">
        <div class="tips" style="padding:15px;">
            <p style=" list-style: 160%; margin: 10px 0;">Hi,</p>
            <p style=" list-style: 160%; margin: 10px 0;">${First}</p>
        </div>
        <div class="time" style="text-align: right; color: #999; padding: 0 15px 15px;">${Second}</div>
        <br>
        <table class="list" style="width: 100%; border-collapse: collapse; border-top:1px solid #eee; font-size:12px;">
            <thead>
            <tr style=" background: #fafafa; color: #333; border-bottom: 1px solid #eee;">
                ${Third}
            </tr>
            </thead>
            <tbody>
            ${Fourth}
            </tbody>
        </table>
    </div>
</div>
</body>
