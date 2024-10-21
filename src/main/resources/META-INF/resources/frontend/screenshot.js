let capture;
let blobValue;
window.capture = async function() {
try {
      const stream = await navigator.mediaDevices.getDisplayMedia({
        preferCurrentTab: true
      });

      const vid = document.createElement("video");

      return new Promise((resolve, reject) => {
        vid.addEventListener("loadedmetadata", function () {
          const canvas = document.createElement("canvas"),
                ctx = canvas.getContext("2d");
          ctx.canvas.width = vid.videoWidth;
          ctx.canvas.height = vid.videoHeight;
          ctx.drawImage(vid, 0, 0, vid.videoWidth, vid.videoHeight);

          stream.getVideoTracks()[0].stop();

          resolve(canvas);
        });

        vid.srcObject = stream;
        vid.play();
      });
    } catch (err) {
        return new Promise((resolve, reject) =>{reject(err)})
    }
}
